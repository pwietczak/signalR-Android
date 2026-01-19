package eu.pw.messageboard.di

import eu.pw.messageboard.presentation.screen.brodcastboard.BroadcastViewModel
import eu.pw.messageboard.presentation.screen.onboarding.OnboardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
	viewModel { OnboardingViewModel(get()) }
	viewModel { BroadcastViewModel() }
}
