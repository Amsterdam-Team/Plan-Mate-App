package logic.usecases

import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.AuthRepository

class LoginUseCase(
    private val authRepository : AuthRepository
) {

    fun validateUserCredentials(username : String, password : String): User{
        val userData = authRepository.login(username,password)
        return userData
    }
}