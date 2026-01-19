package eu.pw.notificationpusher.ui.presentation.screen.main

import androidx.lifecycle.ViewModel
import eu.pw.notificationpusher.domian.event.MessageEvent
import eu.pw.notificationpusher.ui.presentation.domain.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

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
		currentMessages.add( event.message.toUi())
		_uiState.update { currentState ->
			currentState.copy(
				messages = currentMessages.toList(),
			                 )
		}
	}

}