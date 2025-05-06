package data.datasources.userDataSource

import data.datasources.CsvDataSource
import logic.entities.User
import java.util.*

class UserCsvDataSource(
    private val csvDataSource: CsvDataSource<User>

) : UserDataSourceInterface {
    override fun getAllUsers(): List<User> {
        return csvDataSource.getAll() as List<User>
    }

    override fun getUserById(userId: UUID): User {
        TODO("Not yet implemented")
    }

    override fun insertUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateUserName(userId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updatePassword(userId: UUID, newPassword: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun replaceAllUsers(users: List<User>): Boolean {
        TODO("Not yet implemented")
    }
}