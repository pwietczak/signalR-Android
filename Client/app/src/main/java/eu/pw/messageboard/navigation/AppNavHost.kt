package eu.pw.messageboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastBoardScreen
import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastViewModel
import eu.pw.messageboard.presentation.screen.onboarding.OnboardingScreen
import eu.pw.messageboard.presentation.screen.onboarding.OnboardingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
	modifier: Modifier,
	navController: NavHostController,
	onOnboardingFinished: () -> Unit
              ) {
	NavHost(
		navController = navController,
		startDestination = AppDestination.Onboarding,
	       ) {
		composable<AppDestination.Onboarding> {
			OnboardingScreen(
				modifier = modifier,
				viewModel = koinViewModel<OnboardingViewModel>(),
				onEndOnboarding = {
					onOnboardingFinished()
					navController.navigate(AppDestination.BroadcastBoard)
				}
			                )
		}
		composable<AppDestination.BroadcastBoard> {
			BroadcastBoardScreen(
				modifier = modifier,
				viewModel = koinViewModel<BroadcastViewModel>(),
			                    )
		}

	}
}