package logic.repository

import logic.entities.User

interface AuthRepository {
    fun createUser(user: User)

}