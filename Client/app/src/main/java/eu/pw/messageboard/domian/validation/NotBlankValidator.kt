package eu.pw.messageboard.domian.validation

import eu.pw.messageboard.R

class NotBlankValidator: IValidator<String> {
	override fun invoke(input: String): ValidationResult {
		if (input.isBlank())
			return ValidationResult.Error(R.string.validator_error_blank_input)
		return ValidationResult.Success
	}
}