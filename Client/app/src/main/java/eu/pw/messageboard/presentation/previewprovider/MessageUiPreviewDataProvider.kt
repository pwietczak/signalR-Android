package eu.pw.messageboard.presentation.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.messageboard.domian.MessageType
import eu.pw.messageboard.presentation.domain.MessageUi

class MessageUiPreviewDataProvider : PreviewParameterProvider<MessageUi> {

	override val values = sequenceOf(
		MessageUi(
			timeString = "2016-02-15 16:57",
			text = "This is a MessageUi"
		         ),
		MessageUi(
			timeString = "2024-12-31 23:59:59.999",
			text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
					"Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
					"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris."
		         ),
		MessageUi(
			timeString = "2024-12-31 23:59:59.999",
			text = "super_long_one_word_message_without_spaces_to_see_how_overflow_is_gona_act_adding_few_more_words"
		         ),
		MessageUi(
			timeString = "2023-01-01 00:00",
			text = ""
		         ),
		MessageUi(
			timeString = "2023-06-10 12:30",
			text = "     "
		         ),
		MessageUi(
			timeString = "2025-05-20 08:15",
			text = "🔥🚀 Jetpack Compose is awesome 😎✨"
		         ),
		MessageUi(
			timeString = "2022-09-09 09:09:09",
			text = "👍"
		         ),
		MessageUi(
			timeString = "2024-03-14 01:59:26",
			text = "Line 1\nLine 2\nLine 3\nLine 4"
		         ),
		MessageUi(
			timeString = "2025-01-05 18:45",
			text = "Check this out: https://developer.android.com/jetpack/compose",
			type = MessageType.IMPORTANT
		         ),
		MessageUi(
			timeString = "1970-01-01 00:00",
			text = "Unix epoch start"
		         ),
		MessageUi(
			timeString = "2099-12-31 23:59:59",
			text = "System scheduled maintenance completed successfully. Status: OK."
		         )
	                                )
}