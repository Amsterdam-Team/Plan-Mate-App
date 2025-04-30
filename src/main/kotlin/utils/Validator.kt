package utils

import java.util.UUID

object Validator {

    fun isValidUUID(uuid: String?): Boolean = try {
        UUID.fromString(uuid)
        true
    } catch (_: IllegalArgumentException) {
        false
    }

    fun isValidIntegerNumber(input: String?): Boolean {
        return input?.toIntOrNull() != null
    }

    fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrBlank()) return false
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")
        return passwordRegex.matches(password)
    }

    fun isValidName(name: String?): Boolean =
        isNotBlank(name) && hasMinLength(name, 3)

    fun hasMinLength(input: String?, min: Int): Boolean =
        !input.isNullOrEmpty() && input.length >= min

    fun isNotBlank(input: String?): Boolean =
        !input.isNullOrBlank()
}
