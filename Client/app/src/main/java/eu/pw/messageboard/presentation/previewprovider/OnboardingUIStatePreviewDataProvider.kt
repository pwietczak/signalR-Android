package eu.pw.messageboard.presentation.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.messageboard.presentation.screen.onboarding.OnboardingUiState

class OnboardingUIStatePreviewDataProvider : PreviewParameterProvider<OnboardingUiState> {
	override val values = sequenceOf(
		OnboardingUiState(),
		OnboardingUiState(isLoading = true),
	                                )
}