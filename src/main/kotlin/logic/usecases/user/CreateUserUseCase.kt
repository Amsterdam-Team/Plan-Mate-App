package logic.usecases.user

import logic.entities.User
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository
import logic.usecases.validation.ValidateInputUseCase
import logic.usecases.auth.md5Hash
import java.util.UUID

class CreateUserUseCase(private val repository: AuthRepository, private val validateInputUseCase: ValidateInputUseCase) {

    fun execute(username: String, password: String): Boolean {
        if (! validateInputUseCase.isValidName(username)) throw InvalidUsernameException
        validatePassword(password)
        val user = User(
            id = UUID.randomUUID(),
            username = username,
            password = md5Hash(password),
            isAdmin = false
        )
        return repository.createUser(user)

    }


    private fun validatePassword(password:String){
        if (password.isBlank() || password.isEmpty() || password.length < 8){
            throw InvalidPasswordException
        }
    }

}