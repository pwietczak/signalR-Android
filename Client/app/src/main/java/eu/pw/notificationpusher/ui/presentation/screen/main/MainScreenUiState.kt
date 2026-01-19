package eu.pw.notificationpusher.ui.presentation.screen.main

import eu.pw.notificationpusher.ui.presentation.domain.MessageUi

data class MainScreenUiState(
	val messages : List<MessageUi> = listOf()
                            )
