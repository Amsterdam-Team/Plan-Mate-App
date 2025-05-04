package data.repository.auth

import data.datasources.userDataSource.UserDataSourceInterface
import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.AuthRepository

class AuthRepositoryImpl(
    private val userDataSource : UserDataSourceInterface
): AuthRepository {
    override fun createUser(user: User) {
    }

    override fun login(username: String, password: String): User {
        try{
            val user = userDataSource.findUserByCredentials(username,password)
            return user
        }catch (exception: PlanMateException.ValidationException){
            throw exception
        }
    }
}