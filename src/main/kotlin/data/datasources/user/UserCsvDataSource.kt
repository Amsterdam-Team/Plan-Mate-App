package org.amsterdam.planmate.data.datasources.auth

import logic.entities.User

class UserCsvDataSource : UserDataSource {
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