package eu.pw.messageboard.domian.validation

sealed class ValidationResult {
	object Success: ValidationResult()
	data class Error(val errorResId: Int): ValidationResult()
}
