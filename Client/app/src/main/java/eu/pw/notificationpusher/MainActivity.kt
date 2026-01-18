package eu.pw.notificationpusher

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import eu.pw.notificationpusher.data.service.SignalRService
import eu.pw.notificationpusher.ui.presentation.screen.main.MainScreen
import eu.pw.notificationpusher.ui.presentation.screen.main.MainScreenViewModel
import eu.pw.notificationpusher.ui.theme.NotificationPusherTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

	private val signalRService = SignalRService()

	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		signalRService.startSignalRConnection()
		Timber.plant(Timber.DebugTree())
		enableEdgeToEdge()
		setContent {
			NotificationPusherTheme {
				MainScreen(
					modifier = Modifier,
					viewModel = MainScreenViewModel(),
				          )
			}
		}
	}

	override fun onDestroy() {
		Timber.i("OnDestroy()")
		super.onDestroy()
		signalRService.stopSignalRConnection()
	}
}