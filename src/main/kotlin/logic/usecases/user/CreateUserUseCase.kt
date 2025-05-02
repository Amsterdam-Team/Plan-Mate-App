package logic.usecases.user

import logic.entities.User
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository
import ui.utils.md5Hash
import utils.ResultStatus
import java.util.UUID

class CreateUserUseCase(private val repository: AuthRepository) {

    fun execute(username: String, password: String): ResultStatus<User> {
        if (username.isBlank()) return ResultStatus.Error(InvalidUsernameException)
        if (password.isBlank()) return ResultStatus.Error(InvalidPasswordException)

        val user = User(
            id = UUID.randomUUID(),
            username = username,
            password = md5Hash(password),
            isAdmin = false
        )

        return runCatching {
            repository.createUser(user)
            ResultStatus.Success(user)
        }.getOrElse {
            ResultStatus.Error(InvalidUsernameException)
        }
    }
}