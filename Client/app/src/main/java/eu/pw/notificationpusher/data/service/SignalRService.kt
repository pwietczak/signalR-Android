package eu.pw.notificationpusher.data.service

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.TransportEnum
import eu.pw.notificationpusher.BuildConfig
import eu.pw.notificationpusher.domian.event.MessageEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class SignalRService {
	companion object{
		const val RECONNECT_DELAY = 5000L
	}

	private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
	private var hubConnection: HubConnection? = null

	fun startSignalRConnection() {
		// Jeśli już trwa łączenie lub jesteśmy połączeni, nie rób nic
		if (isConnectedOrConnecting()) {
			return
		}

		Timber.i("Starting SignalR hub connection")
		val connectionUrl = constructConnectionUrl()
		Timber.i("Connection URL: $connectionUrl")

		hubConnection = HubConnectionBuilder.create(connectionUrl)
			.withTransport(TransportEnum.LONG_POLLING)
			.build()

		hubConnection?.on(
			"ReceivePush",
			{ message -> scope.launch { onReceivePush(message) } },
			String::class.java,
		                 )

		hubConnection?.onClosed {
			Timber.e("SignalR connection closed. Retrying in ${RECONNECT_DELAY/1000}s...")
			scheduleReconnect()
		}

		try {
			scope.launch(Dispatchers.IO) {
				try {
					hubConnection?.start()?.blockingAwait()
					Timber.i("SignalR started. State: ${hubConnection?.connectionState}")
				} catch (e: Exception) {
					Timber.e("SignalR failed to start, e:$e. Retrying in ${RECONNECT_DELAY / 1000}s...")
					scheduleReconnect()
				}
			}
		} catch (e: Exception) {
			Timber.e("Error during SignalR setup: $e")
		}
	}

	private fun isConnectedOrConnecting(): Boolean {
		return hubConnection?.connectionState != null && hubConnection?.connectionState != HubConnectionState.DISCONNECTED
	}

	private fun constructConnectionUrl(): String {
		val connectionUrl = "http://${BuildConfig.SERVER_ADDERSS}/pushHub?userId=2137"
		Timber.i("Connection URL: $connectionUrl")
		return connectionUrl
	}

	private fun scheduleReconnect() {
		scope.launch {
			delay(RECONNECT_DELAY)
			startSignalRConnection()
		}
	}

	private fun onReceivePush(message: String) {
		Timber.i("Service received message: $message. Posting to EventBus.")
		EventBus.getDefault().post(MessageEvent(message))
	}

	fun stopSignalRConnection() {
		Timber.i("Stopping SignalR hub connection")
		hubConnection?.stop()
	}

	data class ImportantMessage(val message: String)
}