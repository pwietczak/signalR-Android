package eu.pw.notificationpusher.ui.presentation.screen.main.components

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
import eu.pw.notificationpusher.domian.MessageType
import eu.pw.notificationpusher.ui.presentation.domain.MessageUi
import eu.pw.notificationpusher.ui.presentation.previewprovider.MessageUiPreviewDataProvider
import eu.pw.notificationpusher.ui.theme.NotificationPusherTheme

@Composable
fun MessageCard(
	modifier: Modifier,
	messageUi: MessageUi,
               ) {
	val cardColors = if (messageUi.type == MessageType.IMPORTANT){
		CardDefaults.cardColors().copy(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
									  )
	}else
	{
		CardDefaults.cardColors()
	}
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.Start,
	      ) {
		Text(
			text = messageUi.timeString,
			color = MaterialTheme.colorScheme.onBackground,
			textAlign = TextAlign.End,
			style = MaterialTheme.typography.bodySmall,
		    )
		Card(
			modifier = Modifier,
			colors = cardColors
		    ) {
			Text(
				text = messageUi.text,
				textAlign = TextAlign.Start,
				overflow = TextOverflow.Visible,
				softWrap = true,
				modifier = Modifier.padding(8.dp),
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
	NotificationPusherTheme {
		Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
			MessageCard(
				modifier = Modifier,
				messageUi = messageUi,
			           )
		}
	}
}