package eu.pw.notificationpusher.ui.presentation.domain

import eu.pw.notificationpusher.domian.Message
import eu.pw.notificationpusher.domian.MessageType

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


