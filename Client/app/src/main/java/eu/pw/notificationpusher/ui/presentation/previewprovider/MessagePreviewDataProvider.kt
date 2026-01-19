package eu.pw.notificationpusher.ui.presentation.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.notificationpusher.ui.presentation.domain.MessageType
import eu.pw.notificationpusher.ui.presentation.domain.MessageUi
import kotlinx.datetime.LocalDateTime

class MessageUiPreviewDataProvider: PreviewParameterProvider<MessageUi> {
	override val values = sequenceOf(
		// 1. Normalna, krótka wiadomość
		MessageUi(
			receiveTime = LocalDateTime(2016, 2, 15, 16, 57, 0, 0),
			text= "This is a MessageUi"
		       ),

		// 2. Bardzo długa wiadomość
		MessageUi(
			receiveTime = LocalDateTime(2024, 12, 31, 23, 59, 59, 999_999_999),
			text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris."
		         ),
		// 2. Bardzo długa wiadomość
		MessageUi(
			receiveTime = LocalDateTime(2024, 12, 31, 23, 59, 59, 999_999_999),
			text = "super_long_one_word_message_without_spaces_to_see_how_overflow_is_gona_act_adding_few_more_words"
		         ),
		// 3. Pusta wiadomość
		MessageUi(
			receiveTime = LocalDateTime(2023, 1, 1, 0, 0, 0, 0),
			text = ""
		       ),

		// 4. Same spacje
		MessageUi(
			receiveTime = LocalDateTime(2023, 6, 10, 12, 30, 0, 0),
			text = "     "
		       ),

		// 5. Emoji i Unicode
		MessageUi(
			receiveTime = LocalDateTime(2025, 5, 20, 8, 15, 0, 0),
			text = "🔥🚀 Jetpack Compose is awesome 😎✨"
		       ),

		// 6. Jeden znak
		MessageUi(
			receiveTime = LocalDateTime(2022, 9, 9, 9, 9, 9, 9),
			text = "👍"
		       ),

		// 7. Wielolinijkowa wiadomość
		MessageUi(
			receiveTime = LocalDateTime(2024, 3, 14, 1, 59, 26, 0),
			text = "Line 1\nLine 2\nLine 3\nLine 4"
		       ),

		// 8. URL w treści
		MessageUi(
			receiveTime = LocalDateTime(2025, 1, 5, 18, 45, 0, 0),
			text = "Check this out: https://developer.android.com/jetpack/compose",
			type = MessageType.IMPORTANT
		       ),

		// 9. Epoch (symbolicznie)
		MessageUi(
			receiveTime = LocalDateTime(1970, 1, 1, 0, 0, 0, 0),
			text = "Unix epoch start"
		       ),

		// 10. Daleka przyszłość
		MessageUi(
			receiveTime = LocalDateTime(2099, 12, 31, 23, 59, 59, 0),
			text = "System scheduled maintenance completed successfully. Status: OK."
		       )
	)
}