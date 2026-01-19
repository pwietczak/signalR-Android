package eu.pw.messageboard

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import eu.pw.messageboard.data.service.SignalRService
import eu.pw.messageboard.presentation.screen.brodcastboard.MainScreen
import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastViewModel
import eu.pw.messageboard.ui.theme.MessageBoardTheme
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
			MessageBoardTheme {
				MainScreen(
					modifier = Modifier,
					viewModel = BroadcastViewModel(),
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