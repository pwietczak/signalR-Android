package eu.pw.messageboard.domian.validation

import org.junit.Test

class NotBlankValidatorTest : ValidatorTest<String>() {

	override val validator = NotBlankValidator()

	@Test
	fun `Empty string input, returns Error`() {
		assertError("")
	}

	@Test
	fun `Whitespace only input, returns Error`() {
		assertError(" ")
		assertError("  ")
		assertError("   ")
		assertError("    ")
	}

	@Test
	fun `Tabs and newline characters input, returns Error`() {
		assertError("\t")
		assertError("\t\t")
		assertError("\n")
		assertError("\n\n")
		assertError(" \t\n")
	}

	@Test
	fun `Valid non empty string input, returns Success`() {
		assertSuccess("papaj")
	}

	@Test
	fun `Single character input, returns Success`() {
		assertSuccess("p")
	}

	@Test
	fun `Leading and trailing whitespace with content, returns Success`() {
		assertSuccess("  Alice  ")
		assertSuccess("Alice  ")
		assertSuccess("  Alice")
	}

	@Test
	fun `Extremely long valid string, returns Success`() {
		assertSuccess("asdfghjlqwertrlvznxvlkjghghoisdhflksnfsnhbdfjkbsfjkhsakhfoienklsbjnxvlhlkdsjhfoihaenalfbnlkshfoisdhfoshbgklhnsglkhg")
	}

	@Test
	fun `Input contains special characters and whitespaces inside returns Success`() {
		assertSuccess("Pa paj")
		assertSuccess("P apa j")
		assertSuccess("P a p aj")
		assertSuccess("P a p a j")
	}

}