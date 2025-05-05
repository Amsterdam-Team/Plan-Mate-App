package logic.usecases.user

import logic.entities.User
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository
import ui.utils.md5Hash
import utils.ResultStatus
import java.util.UUID

class CreateUserUseCase(private val repository: AuthRepository) {

    fun execute(username: String, password: String): Boolean {
        validateUserName(username)
        validatePassword(password)
        val user = User(
            id = UUID.randomUUID(),
            username = username,
            password = md5Hash(password),
            isAdmin = false
        )
        return repository.createUser(user)

    }

    private fun validateUserName(userName:String){
        if (userName.isBlank() || userName.isEmpty()){
            throw InvalidUsernameException
        }
    }
    private fun validatePassword(password:String){
        if (password.isBlank() || password.isEmpty() || password.length < 8){
            throw InvalidPasswordException
        }
    }

}