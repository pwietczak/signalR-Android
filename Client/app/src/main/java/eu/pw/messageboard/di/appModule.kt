package eu.pw.messageboard.di

import eu.pw.messageboard.domian.mediaplayer.NewMessageSoundPlayer
import eu.pw.messageboard.domian.mediaplayer.INewMessageNotifier
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
	singleOf(::NewMessageSoundPlayer).bind<INewMessageNotifier>()
}