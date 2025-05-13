package data.repository.auth

import data.datasources.userDataSource.IUserDataSource
import logic.entities.User
import logic.repository.IAuthRepository

class AuthRepository(
    private val userDataSource : IUserDataSource
): IAuthRepository {
    override suspend fun createUser(user: User): Boolean {
        return userDataSource.upsertUser(user)
    }

    override suspend fun login(username: String, password: String): User {
        val user = userDataSource.findUserByCredentials(username,password)
        return user
    }
}