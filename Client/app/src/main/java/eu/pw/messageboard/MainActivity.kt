package eu.pw.messageboard

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import eu.pw.messageboard.data.service.SignalRService
import eu.pw.messageboard.navigation.AppNavHost
import eu.pw.messageboard.ui.theme.MessageBoardTheme
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : ComponentActivity() {

	private val signalRService: SignalRService by inject()

	@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			MessageBoardTheme {
				val navController = rememberNavController()
				Scaffold {
					AppNavHost(
						modifier = Modifier,
						navController = navController,
						onOnboardingFinished = {
							Timber.i("onOnboardingFinished()")
							signalRService.startSignalRConnection()
						},
					          )
				}
			}
		}
	}

	override fun onDestroy() {
		Timber.i("OnDestroy()")
		super.onDestroy()
		signalRService.stopSignalRConnection()
	}
}