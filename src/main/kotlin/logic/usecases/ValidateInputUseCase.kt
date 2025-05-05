package logic.usecases

import java.util.UUID

class ValidateInputUseCase {
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() &&
                name.length in 3..100 &&
                name.matches(Regex("^[\\w\\s-]+$"))
    }

    fun isValidUUID(uuid: String): Boolean = try {
        UUID.fromString(uuid)
        true
    } catch (_: IllegalArgumentException) {
        false
    }
}