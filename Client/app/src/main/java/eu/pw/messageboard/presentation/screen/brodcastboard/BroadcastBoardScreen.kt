package eu.pw.messageboard.presentation.screen.brodcastboard

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.messageboard.R
import eu.pw.messageboard.presentation.previewprovider.BroadcastBoardUiStatePreviewProvider
import eu.pw.messageboard.presentation.screen.brodcastboard.components.BroadcastBoardTopBar
import eu.pw.messageboard.presentation.screen.brodcastboard.components.MessageCard
import eu.pw.messageboard.ui.theme.MessageBoardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastBoardScreen(
	modifier: Modifier,
	viewModel: BroadcastViewModel,
                        ) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val listState = rememberLazyListState()

	LaunchedEffect(uiState.value.messages.size) {
		listState.animateScrollToItem(uiState.value.messages.size)
	}

	BroadcastBoardScreenContent(
		modifier = modifier,
		uiState = uiState.value,
		listState = listState,
	                           )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastBoardScreenContent(
	modifier: Modifier,
	uiState: BroadcastBoardUiState,
	listState: LazyListState,
	onGoToSettings: () -> Unit = {},
                               ) {
	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			BroadcastBoardTopBar(
				modifier = Modifier,
				onGoToSettings = onGoToSettings
								)
		},
	        ) { innerPadding ->
		if (uiState.messages.isEmpty()) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
				contentAlignment = Alignment.Center,
			   ) {
				Text(
					text = stringResource(R.string.broadcast_board_no_messages),
					style = MaterialTheme.typography.bodyLarge,
				    )
			}
		}
		else {
			LazyColumn(
				horizontalAlignment = Alignment.Start,
				verticalArrangement = Arrangement.spacedBy(4.dp),
				state = listState,
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding)
					.padding(horizontal = 16.dp),
			          ) {
				item {
					Spacer(modifier = Modifier.size(4.dp))
				}
				items(
					items = uiState.messages,
					key = { it.hashCode() },
				     ) {
					MessageCard(
						modifier = Modifier.animateItem(
							fadeInSpec = tween(durationMillis = 500),
							placementSpec = spring(stiffness = Spring.StiffnessMedium),
						                               ),
						messageUi = it,
					           )
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun MessageBoardPreview(
	@PreviewParameter(BroadcastBoardUiStatePreviewProvider::class) uiState: BroadcastBoardUiState,
                       ) {
	MessageBoardTheme {
		BroadcastBoardScreenContent(
			modifier = Modifier,
			uiState = uiState,
			listState = rememberLazyListState(),
			onGoToSettings = { },
		                           )
	}
}
