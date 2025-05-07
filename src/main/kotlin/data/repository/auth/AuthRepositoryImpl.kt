package data.repository.auth

import data.datasources.userDataSource.UserDataSourceInterface
import logic.entities.User
import logic.repository.AuthRepository

class AuthRepositoryImpl(
    private val userDataSource : UserDataSourceInterface
): AuthRepository {
    override fun createUser(user: User) =
        userDataSource.insertUser(user)


    override fun login(username: String, password: String)=
       userDataSource.findUserByCredentials(username,password)

}