package eu.pw.notificationpusher.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import eu.pw.notificationpusher.BuildConfig
import eu.pw.notificationpusher.R
import eu.pw.notificationpusher.event.MessageEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class PushForegroundService : Service()
{
	private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

	companion object
	{
		private const val PUSH_NOTIFICATION_CHANNEL_ID = "push_channel"
		private const val SERVICE_NOTIFICATION_TITLE = "Notification Pusher"
		private const val SERVICE_NOTIFICATION_TEXT = "Listening for new notifications."


		fun createNotificationChannel(context: Context)
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				Timber.i("Creating notification channel")
				val name = "Push Notifications"
				val importance = NotificationManager.IMPORTANCE_HIGH
				val channel = NotificationChannel(PUSH_NOTIFICATION_CHANNEL_ID, name, importance)
				val notificationManager: NotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

				notificationManager.createNotificationChannel(channel)
			}
		}
	}

	private var hubConnection: HubConnection? = null


	override fun onCreate()
	{
		super.onCreate()
		Timber.i("onCreate()")
		startForeground(1, createNotification())
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
	{
		Timber.i("Service started/restarted")
		startSignalR()
		return START_STICKY
	}

	private fun startSignalR()
	{
		Timber.i("Starting SignalR hub connection")
		val connectionUrl = "http://${BuildConfig.SERVER_ADDERSS}/pushHub?userId=2137"
		Timber.i("Connection URL: $connectionUrl")
		hubConnection = HubConnectionBuilder
				.create(connectionUrl)
				.withTransport(TransportEnum.LONG_POLLING)
				.build()

		hubConnection?.on(
				"ReceivePush",
				{ message -> scope.launch { onReceivePush(message) } },
				String::class.java
						)

		hubConnection?.onClosed {
			Timber.e("SignalR connection closed")
		}

		try {
			hubConnection?.start()?.blockingAwait()
			Timber.i("SignalR started. State: ${hubConnection?.connectionState}")
		} catch (e: Exception) {
			Timber.e("SignalR failed to start, e:$e")
		}
	}

	private fun onReceivePush(message: String) {
		Timber.i("Service hash:'${this.hashCode()}'. Service received message: $message. Posting to EventBus.")
		EventBus.getDefault().post(MessageEvent(message))

	}

	private fun createNotification(): Notification {
		return NotificationCompat.Builder(this, PUSH_NOTIFICATION_CHANNEL_ID)
				.setContentTitle(SERVICE_NOTIFICATION_TITLE)
				.setContentText(SERVICE_NOTIFICATION_TEXT)
				.setSmallIcon(R.drawable.round_notifications_active_24)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.build()
	}

	override fun onBind(intent: Intent?): IBinder? = null

	override fun onDestroy() {
		super.onDestroy()
		Timber.i("onDestroy() - Stopping SignalR connection.")
		if (hubConnection != null) {
			hubConnection?.stop()
		}
	}
}

