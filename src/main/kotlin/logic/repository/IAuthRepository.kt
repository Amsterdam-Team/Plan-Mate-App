package logic.repository

import logic.entities.User

interface IAuthRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun login(username : String , password : String): User
}