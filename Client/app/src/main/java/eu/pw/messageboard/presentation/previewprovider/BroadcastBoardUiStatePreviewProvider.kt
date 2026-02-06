package eu.pw.messageboard.presentation.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.messageboard.presentation.model.DisplayableDateTime
import eu.pw.messageboard.presentation.model.MessageUi
import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastBoardUiState
import kotlinx.datetime.LocalDateTime

class BroadcastBoardUiStatePreviewProvider: PreviewParameterProvider<BroadcastBoardUiState> {
	override val values = sequenceOf(
		BroadcastBoardUiState(),
		BroadcastBoardUiState(messages = listOf(
			MessageUi(
				time = DisplayableDateTime(LocalDateTime(2016, 2, 15, 16, 57, 0, 0)),
				text= "This is a MessageUi"
			         )
											   )),
									)
}