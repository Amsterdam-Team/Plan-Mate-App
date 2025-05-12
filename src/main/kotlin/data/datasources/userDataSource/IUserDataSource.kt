package data.datasources.userDataSource

import logic.entities.User
import java.util.UUID

interface IUserDataSource {
    suspend fun getAllUsers(): List<User>

    suspend fun getUserById(userId: UUID): User

    suspend fun upsertUser(user: User): Boolean

    suspend fun deleteUserById(userId: UUID): Boolean

    suspend fun updateUserNameById(userId: UUID, newName: String): Boolean

    suspend fun updatePasswordById(userId: UUID, newPassword: String): Boolean

    suspend fun findUserByCredentials(username:String, password:String):User
}