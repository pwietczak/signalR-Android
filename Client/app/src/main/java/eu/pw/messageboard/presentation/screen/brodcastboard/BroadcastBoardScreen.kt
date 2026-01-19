package eu.pw.messageboard.presentation.screen.brodcastboard

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.messageboard.R
import eu.pw.messageboard.presentation.screen.brodcastboard.components.MessageCard
import eu.pw.messageboard.ui.theme.MessageBoardTheme

@Composable
fun MainScreen(
	modifier: Modifier,
	viewModel: BroadcastViewModel,
              ) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val listState = rememberLazyListState()

	LaunchedEffect(uiState.value.messages.size) {
		listState.animateScrollToItem(uiState.value.messages.size)
	}
	Scaffold(
		modifier = modifier.fillMaxSize(),
	        ) { innerPadding ->
		if (uiState.value.messages.isEmpty()) {
			Box(
				modifier = Modifier.fillMaxSize(),
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
					.fillMaxSize(1f)
					.padding(innerPadding)
					.padding(horizontal = 16.dp),
			          ) {
				items(
					items = uiState.value.messages,
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	MessageBoardTheme { //		MainScreen(modifier = Modifier, snackbarHostState = SnackbarHostState())
	}
}

