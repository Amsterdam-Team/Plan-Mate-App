package logic.usecases.auth

import logic.entities.User
import logic.repository.AuthRepository
import ui.utils.ResultStatus
import java.util.UUID

class CreateUserUseCase(private val repository: AuthRepository) {

    fun execute(username: String, password: String, isAdmin: Boolean): ResultStatus<User> {
        val dummyUser = User(
            id = UUID.randomUUID(),
            username = "",
            password = "",
            isAdmin = false
        )

        return ResultStatus.Success(dummyUser)
    }
}
