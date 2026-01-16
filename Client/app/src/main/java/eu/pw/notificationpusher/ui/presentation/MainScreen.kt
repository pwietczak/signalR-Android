package eu.pw.notificationpusher.ui.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.pw.notificationpusher.ui.theme.NotificationPusherTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MainScreen(
		modifier: Modifier,
		viewModel: MainScreenViewModel
			  )
{
	val snackbarHostState = remember { SnackbarHostState() }


	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { effect ->
			when (effect)
			{
				is MainScreenEffect.ShowSnackbar ->
				{
					launch {
						snackbarHostState.currentSnackbarData?.dismiss()
						Timber.i("Showing snackbar: ${effect.message}")
						snackbarHostState.showSnackbar(
								message = effect.message,
								duration = SnackbarDuration.Short
													  )

					}
				}
			}
		}
	}

	Scaffold(
			modifier = modifier.fillMaxSize(),
			snackbarHost = { SnackbarHost( snackbarHostState) }
			) { innerPadding ->
		Box(
				modifier = Modifier
						.padding(innerPadding)
						.fillMaxSize(), contentAlignment = Alignment.Center
		   ) {
			Text(text = "Waiting for broadcast...")
		}
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
	NotificationPusherTheme {
//		MainScreen(modifier = Modifier, snackbarHostState = SnackbarHostState())
	}
}