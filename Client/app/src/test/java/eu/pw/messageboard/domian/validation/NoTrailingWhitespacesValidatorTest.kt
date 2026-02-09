package eu.pw.messageboard.domian.validation

import org.junit.Test

class NoTrailingWhitespacesValidatorTest: ValidatorTest<String>(){

	override val validator = NoTrailingWhitespacesValidator()

	@Test
	fun `Input contains leading and trailing whitespace with text returns Error`() {
		assertError("  Alice  ")
		assertError("Alice  ")
		assertError("  Alice  ")
		assertError("  Al  ice")
		assertError("\tAlice\t")
		assertError("Alice\t")
		assertError("\tAlice")
		assertError("\tAl  ice")
		assertError("\nAlice\n")
		assertError("Alice\n")
		assertError("\nAlice")
		assertError("\nAl  ice")
	}

	@Test
	fun `Input contains characters and whitespaces inside returns Success`() {
		assertSuccess("Pa paj")
		assertSuccess("P apa j")
		assertSuccess("P a p aj")
		assertSuccess("P a p a j")
	}

}