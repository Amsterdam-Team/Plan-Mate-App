package helper

import logic.entities.User
import java.util.*

fun createUser(
    id: UUID = UUID.randomUUID(),
    isAdmin: Boolean = false,
    username: String = "user1",
    password: String = "123456"
): User = User(
    id = id,
    isAdmin = isAdmin,
    username = username,
    password = password
)