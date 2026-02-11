package eu.pw.messageboard.domian.validation

import eu.pw.messageboard.R

class OnlyDigitOrLetterOrSpaceValidator: IValidator<String> {
	override fun invoke(input: String): ValidationResult {
		if (!input.all { it.isLetterOrDigit() || it == ' ' })
			return ValidationResult.Error(R.string.validator_error_only_digit_or_letter_or_space)
		return ValidationResult.Success
	}
}