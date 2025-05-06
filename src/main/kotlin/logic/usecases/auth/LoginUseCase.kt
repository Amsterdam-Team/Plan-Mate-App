package logic.usecases.auth

import logic.entities.User
import logic.repository.AuthRepository

class LoginUseCase(
    private val authRepository : AuthRepository
) {

    fun validateUserCredentials(username : String, password : String): User{
        val userData = authRepository.login(username, md5Hash(password))
        return userData
    }
}