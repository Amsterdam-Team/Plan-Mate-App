package logic.usecases

import java.util.*

class ValidateInputUseCase {
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length in 3..100 && name.matches(Regex("^[\\w\\s-]+$"))
    }

    fun isValidUUID(uuid: String): Boolean = try {
        UUID.fromString(uuid)
        true
    } catch (_: IllegalArgumentException) {
        false
    }

    fun isValidProjectStates(states: List<String>): Boolean {
        if (states.isEmpty()) return false
        return states.all { state ->
            state.isNotBlank() && state.matches(Regex("^[a-zA-Z]+( [a-zA-Z]+)*$"))
        }
        
    fun areIdentical(oldName: String , newName: String):Boolean{
        return oldName.trim() == newName.trim()
    }
}