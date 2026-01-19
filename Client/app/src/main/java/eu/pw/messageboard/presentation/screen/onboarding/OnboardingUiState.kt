package eu.pw.messageboard.presentation.screen.onboarding

import eu.pw.messageboard.data.preferences.app.AppPreferences

data class OnboardingUiState(
	val isLoading: Boolean = false,
	val appPreferences: AppPreferences = AppPreferences()
                            )
