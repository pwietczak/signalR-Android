package eu.pw.messageboard.domian

enum class MessageType {
	DEFAULT,
	IMPORTANT;

	companion object {
		fun getById(id: Int): MessageType {
			return when (id) {
				1 -> IMPORTANT
				else -> DEFAULT
			}
		}
	}
}