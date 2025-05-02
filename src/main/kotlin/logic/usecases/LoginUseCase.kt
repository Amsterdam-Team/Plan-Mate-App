package logic.usecases

import logic.entities.User
import logic.repository.AuthRepository
import utils.ResultStatus
import java.util.UUID

class LoginUseCase(
    authRepository : AuthRepository
) {

    fun verifyUserState(username : String , password : String): User{
        return User(id = UUID.randomUUID() , username = "Hend", password = "123" , isAdmin = true)
    }
}