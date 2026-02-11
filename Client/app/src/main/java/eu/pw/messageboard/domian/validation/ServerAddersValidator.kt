package eu.pw.messageboard.domian.validation

import eu.pw.messageboard.R

class ServerAddersValidator:IValidator<String> {

	companion object{
		private const val IP_ADDRESS_REGEX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
	}

	override fun invoke(input: String): ValidationResult {
		val parts = input.split(":")
		if (parts.size != 2)
			return ValidationResult.Error(R.string.validator_error_invalid_server_address_parts)

		val ipPart = parts[0]
		val portPart = parts[1]

		val isIpValid = ipPart.matches(IP_ADDRESS_REGEX.toRegex())
		if (!isIpValid)
			return ValidationResult.Error(R.string.validator_error_invalid_ip_format)

		val port = portPart.toIntOrNull()
		val isPortValid = port != null && port in 1..65535
		if (!isPortValid)
			return ValidationResult.Error(R.string.validator_error_invalid_port_format)

		return ValidationResult.Success
	}

}