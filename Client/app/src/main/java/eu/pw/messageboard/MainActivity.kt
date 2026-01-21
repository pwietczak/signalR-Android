package eu.pw.messageboard

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.pw.messageboard.data.service.SignalRService
import eu.pw.messageboard.navigation.AppNavHost
import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastBoardScreen
import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastViewModel
import eu.pw.messageboard.presentation.screen.onboarding.OnboardingScreen
import eu.pw.messageboard.presentation.screen.onboarding.OnboardingViewModel
import eu.pw.messageboard.ui.theme.MessageBoardTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {

	private val signalRService = SignalRService()

	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		signalRService.startSignalRConnection()
		enableEdgeToEdge()
		setContent {
			MessageBoardTheme {
				val navController = rememberNavController()
				AppNavHost(
					modifier = Modifier,
					navController = navController,
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