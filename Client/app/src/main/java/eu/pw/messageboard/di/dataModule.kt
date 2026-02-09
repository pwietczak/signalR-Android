package eu.pw.messageboard.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import eu.pw.messageboard.data.preferences.app.AppPreferencesRepository
import eu.pw.messageboard.data.preferences.app.AppPreferencesSerializer
import eu.pw.messageboard.data.service.SignalRService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
	single { SignalRService(get(), get()) }
	single {
		DataStoreFactory.create(
			serializer = get<AppPreferencesSerializer>(),
			produceFile = { androidContext().dataStoreFile("app_preferences.json") }
		                       )
	}
	single { AppPreferencesRepository(get()) }
	single { AppPreferencesSerializer }
}