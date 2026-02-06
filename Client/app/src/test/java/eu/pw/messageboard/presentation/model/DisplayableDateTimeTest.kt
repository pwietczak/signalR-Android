package eu.pw.messageboard.presentation.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Test

class DisplayableDateTimeTest {

	@Test
	fun `returns formatted hour and minute when date is today`() {
		val today = LocalDate(2025, 3, 19)
		val dateTime = LocalDateTime(today, LocalTime(9, 5))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("09:05", result)
	}

	@Test
	fun `returns formatted hour and minute when date is today, without seconds`() {
		val today = LocalDate(2025, 3, 19)
		val dateTime = LocalDateTime(today, LocalTime(6, 9, 59))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("06:09", result)
	}

	@Test
	fun `returns formatted hour and minute when date is today, without seconds and nanoseconds`() {
		val today = LocalDate(2025, 3, 19)
		val dateTime = LocalDateTime(today, LocalTime(21, 37, 59, 999999999))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("21:37", result)
	}

	@Test
	fun `handles midnight correctly`() {
		val today = LocalDate(2025, 3, 19)
		val dateTime = LocalDateTime(today, LocalTime(0, 0))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("00:00", result)
	}

	@Test
	fun `handles end of day correctly`() {
		val today = LocalDate(2025, 3, 19)
		val dateTime = LocalDateTime(today, LocalTime(23, 59))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("23:59", result)
	}

	@Test
	fun `returns date and time when date is different`() {
		val today = LocalDate(2025, 3, 19)
		val otherDate = LocalDate(2025, 3, 18)
		val dateTime = LocalDateTime(otherDate, LocalTime(14, 5))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("2025-03-18 14:05", result)
	}

	@Test
	fun `returns date and time when date is different, without seconds`() {
		val today = LocalDate(2025, 3, 19)
		val otherDate = LocalDate(2025, 3, 18)
		val dateTime = LocalDateTime(otherDate, LocalTime(14, 5, 2))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("2025-03-18 14:05", result)
	}

	@Test
	fun `returns date and time when date is different, without seconds and nanoseconds`() {
		val today = LocalDate(2025, 3, 19)
		val otherDate = LocalDate(2025, 3, 18)
		val dateTime = LocalDateTime(otherDate, LocalTime(14, 5, 6,9999999))

		val result = DisplayableDateTime(dateTime)
			.getFormated(today)

		assertEquals("2025-03-18 14:05", result)
	}
}