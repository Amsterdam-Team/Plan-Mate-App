package data.repository.auth

import logic.entities.User
import logic.repository.AuthRepository

class AuthRepositoryImpl: AuthRepository {
    override fun createUser(user: User) {
    }

    override fun login(username: String, password: String): User {
        TODO("Not yet implemented")
    }
}