package eu.pw.messageboard.presentation.screen.brodcastboard.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.pw.messageboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastBoardTopBar(
	modifier: Modifier,
	onGoToSettings: () -> Unit,
						) {
	TopAppBar(
		modifier = modifier,
		title = {
			Text(
				text = stringResource(R.string.broadcast_board_title),
				color = MaterialTheme.colorScheme.onPrimaryContainer,
			    )
		},
		actions = {
			IconButton(
				onClick = onGoToSettings,
			          ) {
				Icon(
					imageVector = Icons.Default.Settings,
					contentDescription = null,
					tint = MaterialTheme.colorScheme.onPrimaryContainer,
				    )
			}
		},
		colors = TopAppBarDefaults.topAppBarColors().copy(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		                                                 )
	         )
}