package eu.pw.notificationpusher.ui.presentation

sealed interface MainScreenEffect
{
	data class ShowSnackbar(val message: String) : MainScreenEffect
}
