package logic.usecases

import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.AuthRepository

class LoginUseCase(
    private val authRepository : AuthRepository
) {

    fun validateUserCredentials(username : String, password : String): User{
        try{
            val userData = authRepository.login(username,password)
            return userData
        }catch(exception: PlanMateException.ValidationException) {
            throw exception
        }
    }
}