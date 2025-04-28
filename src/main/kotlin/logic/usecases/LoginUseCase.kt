package logic.usecases

import logic.repository.AuthRepository
import utils.ResultStatus

class LoginUseCase(
    authRepository : AuthRepository
) {

    fun verifyUserState(username : String , password : String): Boolean{
        return false
    }
}