package logic.repository

import logic.entities.User

interface AuthRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun login(username : String , password : String): User
}