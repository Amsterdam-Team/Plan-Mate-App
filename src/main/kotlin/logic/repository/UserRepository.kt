package logic.repository

import logic.entities.User

interface UserRepository {
    fun createUser(username: String, password:String, isAdmin:Boolean)
    fun getUserByUsername(username: String) : User
    fun getUsers(): List<User>

}