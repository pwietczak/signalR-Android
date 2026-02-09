package eu.pw.messageboard.presentation.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.messageboard.R

class InputFieldPreviewDataProvider : PreviewParameterProvider<InputFieldPreviewData> {

	val emptyFieldData = InputFieldPreviewData(
		value = "",
		labelResId = R.string.onboarding_user_name_label,
		errorResId = null,
	                                          )
	val errorFieldData = InputFieldPreviewData(
		value = "#error",
		labelResId = R.string.onboarding_user_name_label,
		errorResId = R.string.validator_error_only_digit_or_letter_or_space,
	                                          )

	override val values = sequenceOf(
		emptyFieldData,
		errorFieldData
	                                )
}

data class InputFieldPreviewData(
	val value: String,
	val labelResId: Int,
	val errorResId: Int?,
                                )