package eu.pw.messageboard.navigation

import kotlinx.serialization.Serializable

sealed class AppDestination {
	@Serializable
	object Onboarding : AppDestination()

	@Serializable
	object BroadcastBoard : AppDestination()
}