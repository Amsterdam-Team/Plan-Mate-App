package data.datasources.userDataSource

import logic.entities.User
import java.util.UUID

interface UserDataSourceInterface {
    suspend fun getAllUsers(): List<User>

    suspend fun getUserById(userId: UUID): User

    suspend fun insertUser(user: User): Boolean

    suspend fun deleteUser(userId: UUID): Boolean

    suspend fun updateUserName(userId: UUID, newName: String): Boolean

    suspend fun updatePassword(userId: UUID, newPassword: String): Boolean

    suspend fun replaceAllUsers(users: List<User>): Boolean

    suspend fun findUserByCredentials(username:String, password:String):User
}