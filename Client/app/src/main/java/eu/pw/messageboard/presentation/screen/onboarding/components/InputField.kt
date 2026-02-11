package eu.pw.messageboard.presentation.screen.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import eu.pw.messageboard.presentation.previewprovider.InputFieldPreviewData
import eu.pw.messageboard.presentation.previewprovider.InputFieldPreviewDataProvider
import eu.pw.messageboard.ui.theme.MessageBoardTheme

@Composable
fun InputField(
	modifier: Modifier,
	value: String,
	onValueChange: (String) -> Unit,
	labelResId: Int,
	errorResId: Int?,
              ) {
	OutlinedTextField(
		modifier = modifier,
		value = value,
		isError = errorResId != null,
		supportingText = {
			errorResId?.let {
				Text(stringResource(errorResId))
			}
		},
		onValueChange = onValueChange,
		label = { Text(stringResource(labelResId)) },
	                 )
}

@PreviewLightDark
@Composable
fun InputFieldPreview(
	@PreviewParameter(
		InputFieldPreviewDataProvider::class,
	                 ) data: InputFieldPreviewData,
                     ) {
	MessageBoardTheme {
		Surface(modifier = Modifier
			.background(MaterialTheme.colorScheme.background)
			.padding(16.dp)
		       ) {
			InputField(
				modifier = Modifier,
				value = data.value,
				onValueChange = {},
				labelResId = data.labelResId,
				errorResId = data.errorResId,
			          )
		}
	}
}