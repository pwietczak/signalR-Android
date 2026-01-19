package eu.pw.messageboard.domian

import kotlinx.datetime.LocalDateTime

data class Message (
	val receiveTime: LocalDateTime,
	val type: MessageType = MessageType.DEFAULT,
	val text: String
                )




