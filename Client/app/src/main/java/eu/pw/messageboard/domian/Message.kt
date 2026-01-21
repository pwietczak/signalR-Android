package eu.pw.messageboard.domian

import eu.pw.messageboard.data.domain.MessageDto
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class Message (
	val receiveTime: LocalDateTime,
	val type: MessageType = MessageType.DEFAULT,
	val text: String
                )

fun MessageDto.toMessage(): Message {
	return Message(
		receiveTime = Clock.System.now().toLocalDateTime(
			TimeZone.currentSystemDefault(),
		                                                ),
		type = MessageType.getById(type),
		text = text,
	              )
}




