package eu.pw.messageboard.data.service

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.TransportEnum
import eu.pw.messageboard.BuildConfig
import eu.pw.messageboard.data.domain.MessageDto
import eu.pw.messageboard.data.domain.ResponseDto
import eu.pw.messageboard.domian.Message
import eu.pw.messageboard.domian.MessageType
import eu.pw.messageboard.domian.event.MessageEvent
import eu.pw.messageboard.domian.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import kotlin.time.Clock

class SignalRService {
	companion object {
		const val RECONNECT_DELAY = 15_000L
		const val HANDSHAKE_CONNECTION_TIMEOUT = 15_000L
		const val USER_ID = 2137
		const val RECEIVE_MESSAGE_METHOD_NAME = "ReceiveMessage"
		const val SEND_RESPONSE_METHOD_NAME = "GetAck"
	}

	private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
	private var hubConnection: HubConnection? = null

	fun startSignalRConnection() {
		if (isConnectedOrConnecting()) {
			return
		}

		Timber.i("Starting SignalR hub connection")
		val connectionUrl = constructConnectionUrl()

		hubConnection =
			HubConnectionBuilder.create(connectionUrl).withTransport(TransportEnum.LONG_POLLING)
				.withHandshakeResponseTimeout(HANDSHAKE_CONNECTION_TIMEOUT).build()

		hubConnection!!.on(
			RECEIVE_MESSAGE_METHOD_NAME,
			{ messageDto -> scope.launch { onReceiveMessage(messageDto) } },
			MessageDto::class.java,
		                 )

		hubConnection!!.onClosed {
			Timber.e("SignalR connection closed. Retrying in ${RECONNECT_DELAY / 1000}s...")
			scheduleReconnect()
		}

		try {
			scope.launch(Dispatchers.IO) {
				try {
					hubConnection!!.start()!!.blockingAwait()
					Timber.i("SignalR started. State: ${hubConnection?.connectionState}")
				}
				catch (e: Exception) {
					Timber.e(
						"SignalR failed to start, e:$e. Retrying in ${RECONNECT_DELAY / 1000}s...",
					        )
					scheduleReconnect()
				}
			}
		}
		catch (e: Exception) {
			Timber.e("Error during SignalR setup: $e")
		}
	}

	private fun isConnectedOrConnecting(): Boolean {
		val isConnectionStateNull = hubConnection?.connectionState == null
		val isConnectionStateDisconnected =
			hubConnection?.connectionState == HubConnectionState.DISCONNECTED
		Timber.i(
			"isConnectionStateNull: $isConnectionStateNull, isConnectionStateDisconnected: $isConnectionStateDisconnected",
		        )
		return !isConnectionStateNull && !isConnectionStateDisconnected
	}

	private fun constructConnectionUrl(): String {
		val connectionUrl = "http://${BuildConfig.SERVER_ADDERSS}/messageHub?userId=${USER_ID}"
		Timber.i("Constructed SignalR connection URL: $connectionUrl")
		return connectionUrl
	}

	private fun sendResponse(responseDto: ResponseDto): Boolean{
		if (hubConnection == null) {
			Timber.i("SignalR connection is null. Not sending message.")
			return false
		}
		if (hubConnection?.connectionState != HubConnectionState.CONNECTED) {
			Timber.i("SignalR connection is not connected. Not sending message.")
			return false
		}
		hubConnection!!.send(SEND_RESPONSE_METHOD_NAME, responseDto)
		return true
	}

	private fun scheduleReconnect() {
		scope.launch {
			delay(RECONNECT_DELAY)
			Timber.i("Reconnecting to SignalR...")
			startSignalRConnection()
		}
	}

	private fun onReceiveMessage( messageDto: MessageDto
	                            ) {
		sendResponseIfNeeded(messageDto)
		val message = messageDto.toMessage()
		Timber.i("Service received $message. Posting to EventBus.")
		EventBus.getDefault().post(
			MessageEvent(
				message = messageDto.toMessage(),
			            ),
		                          )
	}

	private fun sendResponseIfNeeded(messageDto: MessageDto){
		if (MessageType.getById(messageDto.type) != MessageType.IMPORTANT)
			return
		Timber.i("Service received important message. Sending response.")
		val responseDto = ResponseDto(
			messageId = messageDto.id,
			status = 1,
		)
		sendResponse(responseDto)


	}

	fun stopSignalRConnection() {
		Timber.i("Stopping SignalR hub connection")
		hubConnection?.stop()
	}
}