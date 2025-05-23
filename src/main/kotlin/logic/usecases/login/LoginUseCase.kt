package logic.usecases.login

import logic.entities.User
import logic.repository.IAuthRepository
import logic.usecases.utils.StateManager
import ui.utils.md5Hash

class LoginUseCase(
    private val authRepository : IAuthRepository,
    private val stateManager: StateManager
) {

    suspend fun validateUserCredentials(username : String, password : String): User {
        val userData = authRepository.login(username, md5Hash(password))
        stateManager.setLoggedInUser(userData)
        return userData
    }
}