package eu.pw.notificationpusher

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import eu.pw.notificationpusher.service.SignalRService
import eu.pw.notificationpusher.ui.presentation.MainScreen
import eu.pw.notificationpusher.ui.presentation.MainScreenViewModel
import eu.pw.notificationpusher.ui.theme.NotificationPusherTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

	private val signalRService = SignalRService()

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

				//				var hasNotificationPermission by remember {
				//					mutableStateOf(
				//							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
				//								ContextCompat.checkSelfPermission(
				//										this,
				//										Manifest.permission.POST_NOTIFICATIONS
				//																 ) == PackageManager.PERMISSION_GRANTED
				//							else true
				//								  )
				//				}

				//				if (hasNotificationPermission) {
				//					MainScreen(
				//							modifier = Modifier,
				//							viewModel = MainScreenViewModel()
				//							  )
				//				} else {
				//					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				//						InitialScreen(
				//								onPermissionGranted = {
				//									hasNotificationPermission = true
				//								}
				//										 )
				//					}
				//				}
			}
		}
	}

	override fun onDestroy() {
		Timber.i("OnDestroy()")
		super.onDestroy()
		signalRService.stopSignalRConnection()
	}

	//	override fun onStart() {
	//		super.onStart()
	//		PushForegroundService.createNotificationChannel(this)
	//		restartPushService()
	//	}

	//	private fun restartPushService() {
	//		val serviceIntent = Intent(this, PushForegroundService::class.java)
	//		stopService(serviceIntent)
	//		startService(serviceIntent)
	//	}
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun InitialScreen(
	onPermissionGranted: () -> Unit,
                 ) {
	Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues),
			contentAlignment = Alignment.Center,
		   ) {
			val context = LocalContext.current
			val launcher = rememberLauncherForActivityResult(
				ActivityResultContracts.RequestPermission(),
			                                                ) { isGranted ->
				if (isGranted) {
					Timber.d("PERMISSION GRANTED")
					onPermissionGranted()
				}
				else {
					Timber.d("PERMISSION DENIED")
				}
			}

			Button(
				onClick = {
					when {
						ContextCompat.checkSelfPermission(
							context,
							Manifest.permission.POST_NOTIFICATIONS,
						                                 ) == PackageManager.PERMISSION_GRANTED -> {
							onPermissionGranted()
						}

						else -> {
							launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
						}
					}
				},
			      ) {
				Text(text = "Check and Request Permission")
			}
		}
	}
}
