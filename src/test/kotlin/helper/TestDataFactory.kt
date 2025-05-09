package helper


import logic.entities.User
import java.util.UUID

object TestDataFactory {

    fun createUser(
        id: UUID = UUID.randomUUID(),
        username: String = "mohammad",
        password: String = "123456",
        isAdmin: Boolean = false
    ): User {
        return User(
            id = id,
            username = username,
            password = password,
            isAdmin = isAdmin
        )
    }


}