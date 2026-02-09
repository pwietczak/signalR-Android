package eu.pw.messageboard.domian.validation

import org.junit.Assert.assertTrue

abstract class ValidatorTest <T>{
	protected abstract val validator: IValidator<T>

	protected fun assertSuccess(input: T){
		assertTrue(validator(input) is ValidationResult.Success)
	}

	protected fun assertError(input: T){
		assertTrue(validator(input) is ValidationResult.Error)
	}
}