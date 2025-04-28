package org.amsterdam.planmate.data.repository.auth

import logic.entities.User
import logic.repository.UserRepository
import org.amsterdam.planmate.data.datasources.auth.UserDataSource

class UserRepository(val userDataSource: UserDataSource): UserRepository {
    override fun createUser(username: String, password: String, isAdmin: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

}