package eu.pw.notificationpusher.ui.presentation.domain

import kotlinx.datetime.LocalDateTime

data class MessageUi (
	val receiveTime: LocalDateTime,
	val type: MessageType = MessageType.DEFAULT,
	val text: String
                )

enum class MessageType {
	DEFAULT,
	IMPORTANT;

	companion object {
		fun getById(id: Int): MessageType {
			return when (id) {
				1 -> IMPORTANT
				else -> DEFAULT
			}
		}
	}
}


