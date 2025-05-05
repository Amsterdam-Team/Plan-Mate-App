package data.repository.auth

import data.datasources.userDataSource.UserDataSourceInterface
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.WrongPasswordException
import logic.exception.PlanMateException.AuthorizationException.WrongUsernameException
import logic.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: UserDataSourceInterface
) : AuthRepository {
    override fun createUser(user: User) {
    }

    override fun login(username: String, password: String): User {
        val allUsers = dataSource.getAllUsers()
        val user = findUser(username, allUsers) ?: throw WrongUsernameException
        if (user.password != password) WrongPasswordException

        return user
    }

    private fun findUser(username: String, allUsers: List<Any>): User? {
        return allUsers
            .map { it as User }
            .find { it.username == username }
    }
}