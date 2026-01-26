package eu.pw.messageboard.data.service

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.TransportEnum
import eu.pw.messageboard.data.domain.MessageDto
import eu.pw.messageboard.data.domain.ResponseDto
import eu.pw.messageboard.data.preferences.app.AppPreferencesRepository
import eu.pw.messageboard.domian.MessageType
import eu.pw.messageboard.domian.event.MessageEvent
import eu.pw.messageboard.domian.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class SignalRService (
	private val appPreferencesRepository: AppPreferencesRepository
					 ){
	companion object {
		const val RECONNECT_DELAY = 15_000L
		const val HANDSHAKE_CONNECTION_TIMEOUT = 15_000L
		const val USER_ID_HEADER_NAME = "UserId"
		const val RECEIVE_MESSAGE_METHOD_NAME = "ReceiveMessage"
		const val SEND_RESPONSE_METHOD_NAME = "GetAck"
	}

	private var userId: String? = null
	private var serverAddress: String? = null
	private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
	private var hubConnection: HubConnection? = null

	fun startSignalRConnection() {
		scope.launch {
			try {
				val prefs = appPreferencesRepository.appPreferencesFlow.first()
				userId = prefs.username
				serverAddress = prefs.serverAddress
				Timber.i("Loaded preferences, userID: $userId, server address: $serverAddress")
			} catch (e: Exception) {
				Timber.e(e, "Failed to load preferences for SignalR")
				return@launch
			}

			if (isConnectedOrConnecting()) {
				Timber.i("SignalR already connected or connecting")
				return@launch
			}

			Timber.i("Starting SignalR hub connection")
			val connectionUrl = constructConnectionUrl() ?: return@launch

			val newHubConnection = HubConnectionBuilder
				.create(connectionUrl)
				.withTransport(TransportEnum.WEBSOCKETS)
				.withHeader(USER_ID_HEADER_NAME, userId!!)
				.withHandshakeResponseTimeout(HANDSHAKE_CONNECTION_TIMEOUT)
				.build()

			newHubConnection.on(
				RECEIVE_MESSAGE_METHOD_NAME,
				{ messageDto -> scope.launch { onReceiveMessage(messageDto) } },
				MessageDto::class.java,
			)

			newHubConnection.onClosed {
				Timber.e("SignalR connection closed.")
				reconnect()
			}

			hubConnection = newHubConnection

			launch(Dispatchers.IO) {
				try {
					hubConnection?.start()?.blockingAwait()
					Timber.i("SignalR started. State: ${hubConnection?.connectionState}")
				} catch (e: Exception) {
					Timber.e("SignalR failed to start, e:$e.")
					reconnect()
				}
			}
		}
	}

	private fun isConnectedOrConnecting(): Boolean {
		val state = hubConnection?.connectionState
		val result = state != null && state != HubConnectionState.DISCONNECTED
		Timber.i("isConnectedOrConnecting: $result (state: $state)")
		return result
	}

	private fun constructConnectionUrl(): String? {
		val address = serverAddress ?: return null
		val connectionUrl = "http://${address}/messageHub"
		Timber.i("Constructed SignalR connection URL: $connectionUrl")
		return connectionUrl
	}

	private fun sendResponse(responseDto: ResponseDto): Boolean{
		val connection = hubConnection
		if (connection == null) {
			Timber.i("SignalR connection is null. Not sending message.")
			return false
		}
		if (connection.connectionState != HubConnectionState.CONNECTED) {
			Timber.i("SignalR connection is not connected. Not sending message.")
			return false
		}
		connection.send(SEND_RESPONSE_METHOD_NAME, responseDto)
		return true
	}

	private var reconnectJob: Job? = null

	private fun reconnect() {
		if (reconnectJob?.isActive == true) {
			Timber.i("Reconnect already running")
			return
		}

		reconnectJob = scope.launch(Dispatchers.IO) {
			Timber.i("Reconnect loop started")

			while (isActive) {
				if (hubConnection?.connectionState == HubConnectionState.CONNECTED) {
					Timber.i("Already connected, stopping reconnect loop")
					break
				}

				try {
					Timber.i("Trying to reconnect...")
					hubConnection?.start()?.blockingAwait()
					Timber.i("Reconnected successfully")
					break
				} catch (e: Exception) {
					Timber.e(e, "Reconnect failed, retrying in ${RECONNECT_DELAY / 1000}s")
					delay(RECONNECT_DELAY)
				}
			}
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