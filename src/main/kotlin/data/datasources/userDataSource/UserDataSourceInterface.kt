package data.datasources.userDataSource

import logic.entities.User
import java.util.UUID

interface UserDataSourceInterface {
    fun getAllUsers(): List<User>

    fun getUserById(userId: UUID): User

    fun insertUser(user: User): Boolean

    fun deleteUser(userId: UUID): Boolean

    fun updateUserName(userId: UUID, newName: String): Boolean

    fun updatePassword(userId: UUID, newPassword: String): Boolean

    fun replaceAllUsers(users: List<User>): Boolean
}