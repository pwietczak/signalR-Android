package eu.pw.messageboard.presentation.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.messageboard.domian.MessageType
import eu.pw.messageboard.presentation.model.DisplayableDateTime
import eu.pw.messageboard.presentation.model.MessageUi
import kotlinx.datetime.LocalDateTime

class MessageUiPreviewDataProvider : PreviewParameterProvider<MessageUi> {
	override val values = sequenceOf(
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2016, 2, 15, 16, 57, 0, 0)),
			text= "This is a MessageUi"
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2024, 12, 31, 23, 59, 59, 999_999_999)),
			text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris."
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2024, 12, 31, 23, 59, 59, 999_999_999)),
			text = "super_long_one_word_message_without_spaces_to_see_how_overflow_is_gona_act_adding_few_more_words"
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2023, 1, 1, 0, 0, 0, 0)),
			text = ""
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2023, 6, 10, 12, 30, 0, 0)),
			text = "     "
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2025, 5, 20, 8, 15, 0, 0)),
			text = "🔥🚀 Jetpack Compose is awesome 😎✨"
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2022, 9, 9, 9, 9, 9, 9)),
			text = "👍"
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2024, 3, 14, 1, 59, 26, 0)),
			text = "Line 1\nLine 2\nLine 3\nLine 4"
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2025, 1, 5, 18, 45, 0, 0)),
			text = "Check this out: https://developer.android.com/jetpack/compose",
			type = MessageType.IMPORTANT
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(1970, 1, 1, 0, 0, 0, 0)),
			text = "Unix epoch start"
		         ),
		MessageUi(
			time = DisplayableDateTime(LocalDateTime(2099, 12, 31, 23, 59, 59, 0)),
			text = "System scheduled maintenance completed successfully. Status: OK."
		         )
	                                )
}