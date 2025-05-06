package data.repository.auth

import data.datasources.userDataSource.UserDataSourceInterface
import logic.entities.User
import logic.repository.AuthRepository

class AuthRepositoryImpl(
    private val userDataSource : UserDataSourceInterface
): AuthRepository {
    override fun createUser(user: User): Boolean {
        return true
    }

    override fun login(username: String, password: String): User {
        val user = userDataSource.findUserByCredentials(username,password)
        return user
    }
}