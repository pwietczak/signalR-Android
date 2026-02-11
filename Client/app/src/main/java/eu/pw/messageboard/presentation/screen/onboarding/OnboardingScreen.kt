package eu.pw.messageboard.presentation.screen.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.messageboard.R
import eu.pw.messageboard.presentation.previewprovider.OnboardingUIStatePreviewDataProvider
import eu.pw.messageboard.presentation.screen.onboarding.components.InputField
import eu.pw.messageboard.ui.theme.MessageBoardTheme

@Composable
fun OnboardingScreen(
	modifier: Modifier,
	viewModel: OnboardingViewModel,
	onEndOnboarding: () -> Unit,
                    ) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	LaunchedEffect(uiState.appPreferences.isInitialized) {
		if (uiState.appPreferences.isInitialized) {
			onEndOnboarding()
		}
	}
	OnboardingScreenContent(modifier, uiState, viewModel::update)
}

@Composable
fun OnboardingScreenContent(
	modifier: Modifier,
	uiState: OnboardingUiState,
	onSaveClick: (String, String) -> Unit,
                           ) {
	Scaffold(modifier = modifier.fillMaxSize()) { scaffoldPadding ->
		if (uiState.isLoading) {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				CircularProgressIndicator()
			}
		}
		else {
			Box(
				Modifier
					.fillMaxSize()
					.padding(scaffoldPadding)
					.padding(16.dp),
			   ) {
				var tempNickname by remember(uiState.appPreferences.username) {
					mutableStateOf(uiState.appPreferences.username)
				}
				var tempServerAddress by remember(uiState.appPreferences.serverAddress) {
					mutableStateOf(uiState.appPreferences.serverAddress)
				}
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.align(Alignment.Center),
					verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
					horizontalAlignment = Alignment.CenterHorizontally,
				      ) {
					InputField(
						modifier = Modifier.fillMaxWidth(),
						value = tempNickname,
						onValueChange = { tempNickname = it },
						errorResId = uiState.userNameError,
						labelResId = R.string.onboarding_user_name_label,
					          )
					InputField(
						modifier = Modifier.fillMaxWidth(),
						value = tempServerAddress,
						onValueChange = { tempServerAddress = it },
						errorResId = uiState.serverAddressError,
						labelResId = R.string.onboarding_server_address_label,
					          )
				}
				Button(
					onClick = { onSaveClick(tempNickname, tempServerAddress) },
					modifier = Modifier.align(Alignment.BottomCenter),
				      ) {
					Text(stringResource(R.string.onboarding_save_button_text))
				}
			}
		}
	}
}

@PreviewLightDark
@Composable
fun OnboardingScreenPreview(
	@PreviewParameter(OnboardingUIStatePreviewDataProvider::class) uiState: OnboardingUiState,
                           ) {
	MessageBoardTheme {
		OnboardingScreenContent(
			modifier = Modifier,
			uiState = uiState,
			onSaveClick = { _, _ -> },
		                       )
	}
}