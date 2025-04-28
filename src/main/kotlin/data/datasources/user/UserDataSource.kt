package org.amsterdam.planmate.data.datasources.auth

import logic.entities.User

interface UserDataSource {
    fun createUser(username: String, password:String, isAdmin:Boolean)
    fun getUserByUsername(username: String) : User
    fun getUsers(): List<User>
}