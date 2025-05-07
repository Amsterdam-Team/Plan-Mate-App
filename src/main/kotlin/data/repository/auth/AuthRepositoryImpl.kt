package data.repository.auth

import data.datasources.userDataSource.IUserDataSource
import logic.entities.User
import logic.repository.AuthRepository

class AuthRepositoryImpl(
    private val userDataSource : IUserDataSource
): AuthRepository {
    override suspend fun createUser(user: User): Boolean {
        return userDataSource.insertUser(user)
    }

    override suspend fun login(username: String, password: String): User {
        val user = userDataSource.findUserByCredentials(username,password)
        return user
    }
}