package ui.utils


import java.util.UUID
import logic.exception.PlanMateException.ValidationException.InvalidUUIDFormatException
import java.lang.IllegalArgumentException

object Validator {

    fun isUUIDValid(id: String?): UUID {
        try {
            return UUID.fromString(id)
        } catch (ex: IllegalArgumentException) {
            throw InvalidUUIDFormatException
        }
    }




}