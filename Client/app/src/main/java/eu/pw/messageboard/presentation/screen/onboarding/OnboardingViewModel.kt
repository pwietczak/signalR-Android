package eu.pw.messageboard.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.pw.messageboard.data.preferences.app.AppPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
	private val appPreferencesRepository: AppPreferencesRepository
                         ): ViewModel() {
	private val _uiState = MutableStateFlow(OnboardingUiState())
	val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()


	init {
		initialiseUiState()
	}

	private fun initialiseUiState() {
		_uiState.update { it.copy(isLoading = true) }

		appPreferencesRepository.appPreferencesFlow
			.onEach { appPreferences ->
				val isLoading = appPreferences.isInitialized
				_uiState.update {
					it.copy(
						appPreferences = appPreferences,
						isLoading = isLoading
					       )
				}
			}.catch {
				_uiState.update { it.copy(isLoading = false) }
			}.launchIn(viewModelScope)
	}

	fun update(userName: String, serverAddress: String) {
		viewModelScope.launch {
			appPreferencesRepository.updateAndInitialize(
				serverAdders = serverAddress,
				username = userName
														)
		}
	}
}