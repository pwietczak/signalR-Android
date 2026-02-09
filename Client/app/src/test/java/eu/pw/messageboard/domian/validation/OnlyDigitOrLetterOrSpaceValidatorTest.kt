package eu.pw.messageboard.domian.validation

import org.junit.Test

class OnlyDigitOrLetterOrSpaceValidatorTest: ValidatorTest<String>() {
	
	override val validator = OnlyDigitOrLetterOrSpaceValidator()
	
	@Test
	fun `Input is an empty string, returns Success `() {
		assertSuccess("")
	}

	@Test
	fun `Input consists only of space characters, returns Success`() {
		assertSuccess(" ")
		assertSuccess("  ")
		assertSuccess("   ")
	}

	@Test
	fun `Input contains leading and trailing whitespace with text, returns Success`() {
		assertSuccess("  Alice  ")
		assertSuccess("Alice  ")
		assertSuccess("Ali23ce  ")
		assertSuccess("  Alic4e")
		assertSuccess("  Alice")
	}

	@Test
	fun `Input is a single non whitespace character, returns Success`() {
		assertSuccess("P")
		assertSuccess("2")
		assertSuccess("5")
		assertSuccess("c")
	}

	@Test
	fun `Input is a standard non empty string, returns Success`() {
		assertSuccess("Papaj")
		assertSuccess("32423")
		assertSuccess("Pa546paj")
	}

	@Test
	fun `Input is a very long string of non whitespace characters returns true`() {
		assertSuccess("asdfghjlqwertrlvznxvlkjghghoisdhflksnfsnhbdfjkbsfjkhsakhfoienklsbjnxvlhlkdsjhfoihaenalfbnlkshfoisdhfoshbgklhnsglkhg")
		assertSuccess("asdfghjl453qwertrlvznxv453lkjghghoisdhflksnf534snhbdfj453kbsfjkhsakhfoi5enkl453sbjnxvlhlkd45sjhfoi354haenalfb4nlksh")
		assertSuccess("1346498764651321654649846513216546546843515168135131678545165135215465465465165198465135135163541684651321654684351")
	}

	@Test
	fun `Input contains special characters and whitespaces inside returns true`() {
		assertSuccess("Pa 6aj")
		assertSuccess("P ap8 j")
		assertSuccess("P a 9 aj")
		assertSuccess("P 6 p a j")
	}

	@Test
	fun `Input contains new line, tabulators, returns Error`() {
		assertError("\n")
		assertError("\t")
		assertError("\r")
		assertError("Pa\n6aj")
		assertError("P\tap8 j")
		assertError("P a\n9\raj")
		assertError("P 6\np\ra\tj")
	}

	@Test
	fun `Input contains special characters, returns Error`() {
		assertError("P@apa j")
		assertError("P  a*paj")
		assertError("P a!p a j")
		assertError("$")
		assertError("234%%")
		assertError("%")
		assertError("234%%")
	}

}