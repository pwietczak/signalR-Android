package eu.pw.messageboard.domian.validation

import org.junit.Test

class ServerAddersValidatorTest: ValidatorTest<String>() {

	override val validator: IValidator<String> = ServerAddersValidator()

	@Test
	fun `Input is an empty string, returns Error `() {
		assertError("")
	}

	@Test
	fun `Input does not contain a colon, returns Error`() {
		assertError("127.0.0.1")
	}

	@Test
	fun `Input contains more than one colon, returns Error`(){
		assertError("127.0.0.1:8080:8081")
	}

	@Test
	fun `Input contains an invalid IP address, returns Error`(){
		assertError("256.0.0.1.4:8080")
		assertError("0.256.0.1:8080")
		assertError("0.0.256.1:8080")
		assertError("0.0.256:8080")
	}

	@Test
	fun `Input contains an different characters than digits returns Error`(){
		assertError("asd.0.0.1:8080")
		assertError("0.256.f.1:8080")
		assertError("0.0.256.1:8d80")
		assertError("0.0.256:8080")
		assertError("0.$.256:80$0")
		assertError("0.0.256:8080")
	}

	@Test
	fun `Input contains an invalid port, returns Error`(){
		assertError("127.0.0.1:65536")
		assertError("127.0.0.1:-1")
	}

	@Test
	fun `Input contains a valid IP address and port, returns Success`(){
		assertSuccess("127.0.0.1:8080")
		assertSuccess("192.168.0.1:8080")
		assertSuccess("10.0.0.1:500")

	}


}