package eu.pw.messageboard.presentation.model

import eu.pw.messageboard.domian.Message
import eu.pw.messageboard.domian.MessageType

data class MessageUi (
	val time: DisplayableDateTime,
	val type: MessageType = MessageType.DEFAULT,
	val text: String
                ) {
}

fun Message.toUi(): MessageUi {
	return MessageUi(
		time = DisplayableDateTime(receiveTime),
		type = type,
		text = text
	       )
}


