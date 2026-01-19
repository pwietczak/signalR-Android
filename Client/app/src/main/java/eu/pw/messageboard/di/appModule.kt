package eu.pw.messageboard.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import eu.pw.messageboard.data.preferences.app.AppPreferencesRepository
import eu.pw.messageboard.data.preferences.app.AppPreferencesSerializer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
	single {
		DataStoreFactory.create(
			serializer = get<AppPreferencesSerializer>(),
			produceFile = { androidContext().dataStoreFile("app_preferences.json") }
		                       )
	}
	single { AppPreferencesRepository(get()) }
	single { AppPreferencesSerializer }
}