package eu.pw.messageboard.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.pw.messageboard.data.preferences.app.AppPreferencesRepository
import eu.pw.messageboard.domian.validation.NoTrailingWhitespacesValidator
import eu.pw.messageboard.domian.validation.NotBlankValidator
import eu.pw.messageboard.domian.validation.OnlyDigitOrLetterOrSpaceValidator
import eu.pw.messageboard.domian.validation.ValidationResult
import eu.pw.messageboard.domian.validation.ValidatorService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class OnboardingViewModel(
	private val appPreferencesRepository: AppPreferencesRepository,
                         ) : ViewModel() {
	private val _uiState = MutableStateFlow(OnboardingUiState())
	val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

	private val userNameValidator = ValidatorService(
		listOf(
			NotBlankValidator(),
			NoTrailingWhitespacesValidator(),
			OnlyDigitOrLetterOrSpaceValidator(),
		      ),
	                                                )
	private val serverAddressValidator = ValidatorService(
		listOf(
			NotBlankValidator(),
			NoTrailingWhitespacesValidator(),
		      ),
	                                                     )

	init {
		initialiseUiState()
	}

	private fun initialiseUiState() {
		_uiState.update { it.copy(isLoading = true) }

		appPreferencesRepository.appPreferencesFlow.onEach { appPreferences ->
				val isLoading = appPreferences.isInitialized
				_uiState.update {
					it.copy(
						appPreferences = appPreferences,
						isLoading = isLoading,
					       )
				}
			}.catch {
				_uiState.update { it.copy(isLoading = false) }
			}.launchIn(viewModelScope)
	}

	fun update(
		userName: String,
		serverAddress: String,
	          ) {
		resetErrorStates()
		if (!isInputDataValid(userName, serverAddress)) {
			Timber.d("Input data is not valid, not updating preferences")
			return
		}
		viewModelScope.launch {
			Timber.d("Updating preferences with userName: $userName, serverAddress: $serverAddress")
			appPreferencesRepository.updateAndInitialize(
				serverAdders = serverAddress,
				username = userName,
			                                            )
		}
	}

	private fun resetErrorStates() {
		_uiState.update {
			it.copy(
				userNameError = null,
				serverAddressError = null,
			       )
		}
	}

	private fun isInputDataValid(
		userName: String,
		serverAddress: String,
	                            ): Boolean {
		val userNameValidation = userNameValidator(userName)
		if (userNameValidation is ValidationResult.Error) {
			_uiState.update { it.copy(userNameError = userNameValidation.errorResId) }
			return false
		}
		val serverAddressValidation = serverAddressValidator(serverAddress)
		if (serverAddressValidation is ValidationResult.Error) {
			_uiState.update { it.copy(serverAddressError = serverAddressValidation.errorResId) }
			return false
		}
		return true
	}
}
