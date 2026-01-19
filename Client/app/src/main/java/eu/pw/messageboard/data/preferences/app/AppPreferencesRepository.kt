package eu.pw.messageboard.data.preferences.app

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.io.IOException

class AppPreferencesRepository(
	private val appPreferencesDataSource: DataStore<AppPreferences>
							  ) {
	val appPreferencesFlow: Flow<AppPreferences> = appPreferencesDataSource.data.catch { exception ->
		if (exception is IOException) {
			Timber.e(exception, "Error reading app preferences")
			emit(AppPreferences())
		} else {
			throw exception
		}
	}

	private suspend fun updateIsInitialized(isInitialized: Boolean) {
		if (isInitialized != appPreferencesFlow.first().isInitialized) {
			appPreferencesDataSource.updateData { currentPreferences ->
				Timber.i("Updating isInitialized to $isInitialized")
				currentPreferences.copy(isInitialized = isInitialized)
			}
		}
	}


	private suspend fun updateServerAddress(serverAdders: String) {
		if (serverAdders != appPreferencesFlow.first().serverAddress) {
			appPreferencesDataSource.updateData { currentPreferences ->
				Timber.i("Updating serverAddress to $serverAdders")
				currentPreferences.copy(serverAddress = serverAdders)
			}
		}
	}

	private suspend fun updateUsername(username: String) {
		if (username != appPreferencesFlow.first().username) {
			appPreferencesDataSource.updateData { currentPreferences ->
				Timber.i("Updating username to $username")
				currentPreferences.copy(username = username)
			}
		}
	}


	suspend fun updateAndInitialize(serverAdders: String, username: String) {
		updateServerAddress(serverAdders)
		updateUsername(username)
		updateIsInitialized(true)
	}

}