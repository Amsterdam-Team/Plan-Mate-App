package data.repository.auth

import data.datasources.DataSource
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.WrongPasswordException
import logic.exception.PlanMateException.AuthorizationException.WrongUsernameException
import logic.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: DataSource
) : AuthRepository {
    override fun createUser(user: User) {
        dataSource.add(user)
    }

    override fun login(username: String, password: String): User {
        val allUsers = dataSource.getAll()
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