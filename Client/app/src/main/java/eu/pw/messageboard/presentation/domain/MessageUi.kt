package eu.pw.messageboard.presentation.domain

import eu.pw.messageboard.domian.Message
import eu.pw.messageboard.domian.MessageType

data class MessageUi (
	val timeString: String,
	val type: MessageType = MessageType.DEFAULT,
	val text: String
                )

fun Message.toUi(): MessageUi {
	return MessageUi(
		timeString = receiveTime.toString()
			.replace('T', ' ')
			.replaceAfterLast('.', "")
			.dropLast(1),
		type = type,
		text = text
	       )
}


