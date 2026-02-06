package eu.pw.messageboard.presentation.screen.brodcastboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import eu.pw.messageboard.domian.MessageType
import eu.pw.messageboard.presentation.model.MessageUi
import eu.pw.messageboard.presentation.previewprovider.MessageUiPreviewDataProvider
import eu.pw.messageboard.ui.theme.MessageBoardTheme

@Composable
fun MessageCard(
	modifier: Modifier,
	messageUi: MessageUi,
               ) {
	val cardColors = if (messageUi.type == MessageType.IMPORTANT) {
		CardDefaults.cardColors().copy(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
		                              )
	}
	else {
		CardDefaults.cardColors()
	}
	Card(
		modifier = modifier,
		colors = cardColors,
	    ) {
		Column(
			modifier = Modifier
				.padding(8.dp),
			horizontalAlignment = Alignment.Start,
		      ) {
			Text(
				text = messageUi.text,
				modifier = Modifier,
				textAlign = TextAlign.Start,
				overflow = TextOverflow.Visible,
			    )
			Text(
				text = messageUi.time.getFormated(),
				modifier = Modifier.align(Alignment.End),
				color = MaterialTheme.colorScheme.onBackground,
				textAlign = TextAlign.End,
				style = MaterialTheme.typography.bodySmall,
			    )
		}
	}
}

@PreviewLightDark
@Composable
fun MessageCardPreview(
	@PreviewParameter(
		MessageUiPreviewDataProvider::class,
	                 ) messageUi: MessageUi,
                      ) {
	MessageBoardTheme {
		Surface(modifier = Modifier
			.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
			MessageCard(
				modifier = Modifier,
				messageUi = messageUi,
			           )
		}
	}
}
