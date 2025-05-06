package logic.usecases.validation

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
    fun areIdentical(oldName: String , newName: String):Boolean{
        return oldName.trim() == newName.trim()
    }
}