package eu.pw.messageboard.data.preferences.app

import kotlinx.serialization.Serializable

@Serializable
data class AppPreferences (
	val isInitialized: Boolean = false,
	val serverAddress: String = "0.0.0.0:5000",
	val username: String = "Guest"
)