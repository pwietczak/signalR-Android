package eu.pw.messageboard.presentation.screen.brodcastboard

import eu.pw.messageboard.presentation.model.MessageUi

data class BroadcastBoardUiState(
	val messages : List<MessageUi> = listOf()
                                )