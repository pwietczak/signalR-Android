package eu.pw.notificationpusher.data.service

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.TransportEnum
import eu.pw.notificationpusher.BuildConfig
import eu.pw.notificationpusher.domian.Message
import eu.pw.notificationpusher.domian.MessageType
import eu.pw.notificationpusher.domian.event.MessageEvent
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

		hubConnection?.on(
			RECEIVE_MESSAGE_METHOD_NAME,
			{ type, text -> scope.launch { onReceiveMessage(type, text) } },
			Int::class.java,
			String::class.java,
		                 )

		hubConnection?.onClosed {
			Timber.e("SignalR connection closed. Retrying in ${RECONNECT_DELAY / 1000}s...")
			scheduleReconnect()
		}

		try {
			scope.launch(Dispatchers.IO) {
				try {
					hubConnection?.start()?.blockingAwait()
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

	private fun scheduleReconnect() {
		scope.launch {
			delay(RECONNECT_DELAY)
			startSignalRConnection()
		}
	}

	private fun onReceiveMessage(
		type: Int,
		text: String,
	                            ) {
		Timber.i("Service received message: $text. Posting to EventBus.")
		EventBus.getDefault().post(
			MessageEvent(
				Message(
					receiveTime = Clock.System.now().toLocalDateTime(
						TimeZone.currentSystemDefault(),
					                                               ),
					type = MessageType.getById(type),
					text = text,
				       ),
			            ),
		                          )
	}

	fun stopSignalRConnection() {
		Timber.i("Stopping SignalR hub connection")
		hubConnection?.stop()
	}
}