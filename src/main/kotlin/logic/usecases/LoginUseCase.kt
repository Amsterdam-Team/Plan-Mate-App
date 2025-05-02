package logic.usecases

import logic.entities.User
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository

class LoginUseCase(
    private val authRepository : AuthRepository
) {

    fun verifyUserState(username : String , password : String): User{
        if(!validateName(username)) throw InvalidUsernameException
        if(!validatePassword(password)) throw InvalidPasswordException

        val userData = authRepository.login(username,password)
        return userData
    }

    private fun validateName(username: String): Boolean =
        Regex("^[A-Za-z][A-Za-z0-9]{2,}$").matches(username)

    private fun validatePassword(password: String): Boolean =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$").matches(password)
}