package eu.pw.notificationpusher.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.pw.notificationpusher.event.MessageEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class MainScreenViewModel(): ViewModel()
{
	private val _sideEffect = MutableSharedFlow<MainScreenEffect>()
	val sideEffect = _sideEffect.asSharedFlow()

	init
	{
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this)
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	fun onMessageEvent(event: MessageEvent) {
		Timber.i("onMessageReceived (from EventBus): ${event.message}")
		viewModelScope.launch {
			_sideEffect.emit(MainScreenEffect.ShowSnackbar(event.message))
		}
	}

}