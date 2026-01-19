package eu.pw.messageboard.data.preferences.app

object AppPreferencesSerializer: eu.pw.messageboard.data.preferences.BasePreferencesSerializer<AppPreferences>(
	defaultInstance = { AppPreferences() },
	serializer = AppPreferences.serializer()
                                                                                                              )