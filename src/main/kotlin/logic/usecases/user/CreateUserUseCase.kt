package logic.usecases.user

import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository
import logic.usecases.StateManager
import logic.usecases.ValidateInputUseCase
import ui.utils.md5Hash
import java.util.UUID

class CreateUserUseCase(
    private val repository: AuthRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val stateManager: StateManager,

    ) {

    suspend fun execute(username: String, password: String): Boolean {
        if (!stateManager.getLoggedInUser().isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidName(username)) throw InvalidUsernameException
        validatePassword(password)
        val user = User(
            id = UUID.randomUUID(),
            username = username,
            password = md5Hash(password),
            isAdmin = false
        )
        return repository.createUser(user)

    }


    private fun validatePassword(password: String) {
        if (password.isBlank() || password.isEmpty() || password.length < 8) {
            throw InvalidPasswordException
        }
    }

}