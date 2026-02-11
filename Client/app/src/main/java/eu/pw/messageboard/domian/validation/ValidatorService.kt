package eu.pw.messageboard.domian.validation

class ValidatorService<T>(private val validatorList: List<IValidator<T>>) {
	operator fun invoke(input: T): ValidationResult {
		for (validator in validatorList) {
			val validationResult = validator(input)
			if (validationResult is ValidationResult.Error) {
				return validationResult
			}
		}
		return ValidationResult.Success
	}
}