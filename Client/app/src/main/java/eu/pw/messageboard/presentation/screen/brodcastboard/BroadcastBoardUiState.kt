package eu.pw.messageboard.presentation.screen.brodcastboard

import eu.pw.messageboard.presentation.domain.MessageUi

data class BroadcastBoardUiState(
	val messages : List<MessageUi> = listOf()
                                )