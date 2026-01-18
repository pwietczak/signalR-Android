package eu.pw.notificationpusher.ui.presentation.screen.main

import androidx.lifecycle.ViewModel
import eu.pw.notificationpusher.domian.event.MessageEvent
import eu.pw.notificationpusher.ui.presentation.domain.MessageUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import kotlin.time.Clock

class MainScreenViewModel() : ViewModel() {
	private val _uiState = MutableStateFlow(MainScreenUiState())
	val uiState = _uiState.asStateFlow()

	init {
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this)
		}
	}


	@Suppress("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	fun onMessageEvent(event: MessageEvent) {
		Timber.i("onMessageReceived (from EventBus): ${event.message}")
		val currentMessages = _uiState.value.messages.toMutableList()
		currentMessages.add(
			MessageUi(
				time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
				text = event.message,
			       ),
		                   )
		_uiState.update { currentState ->
			currentState.copy(
				messages = currentMessages.toList(),
			                 )
		}
	}

}