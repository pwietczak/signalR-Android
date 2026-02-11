package eu.pw.messageboard.domian.validation

interface IValidator<T> {
	operator fun invoke(input: T): ValidationResult
}