package eu.pw.messageboard.domian.validation

import eu.pw.messageboard.R

class NoTrailingWhitespacesValidator: IValidator<String> {
	override fun invoke(input: String): ValidationResult {
		if (input.trim() != input)
			return ValidationResult.Error(R.string.validator_error_trailing_whitespaces)
		return ValidationResult.Success
	}
}