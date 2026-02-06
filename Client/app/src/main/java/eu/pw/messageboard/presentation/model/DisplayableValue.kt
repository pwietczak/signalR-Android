package eu.pw.messageboard.presentation.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class DisplayableDateTime(val value: LocalDateTime) {
    fun getFormated(
	    currentLocalDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
                   ): String {
	    return if (currentLocalDate == value.date) {
		    "%02d:%02d".format(value.hour, value.minute)
	    } else {
			value.date.toString()+" "+"%02d:%02d".format(value.hour, value.minute)
	    }
    }
}