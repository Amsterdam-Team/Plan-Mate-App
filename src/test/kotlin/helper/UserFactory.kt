package helper


import logic.entities.User
import java.util.UUID

object UserFactory {

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
    fun validUserData() = User(
        username = "Hend",
        password = "H123456",
        isAdmin = true,
        id = UUID.fromString("ebcb217c-b373-4e88-afbd-cbb5640a031a")
    )

}