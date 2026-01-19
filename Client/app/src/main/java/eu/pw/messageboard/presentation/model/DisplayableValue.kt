package eu.pw.messageboard.presentation.model

import kotlinx.datetime.LocalDateTime

class DisplayableDateTime(val value: LocalDateTime){
	fun getFormated():String{
		return value.toString()
			.replace('T', ' ')
			.replaceAfterLast('.', "")
			.dropLast(1)
	}
}