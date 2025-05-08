package logic.usecases

import logic.entities.User
import logic.repository.AuthRepository
import ui.utils.md5Hash

class LoginUseCase(
    private val authRepository : AuthRepository,
    private val stateManager: StateManager
) {

    suspend fun validateUserCredentials(username : String, password : String): User{
        val userData = authRepository.login(username, md5Hash(password))
        stateManager.setLoggedInUser(userData)
        return userData
    }
}