package data.datasources.userDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.UserNotFoundException
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import java.util.*

class UserDataSource(
    private val usersCollection: MongoCollection<User>
): IUserDataSource {

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: UUID): User {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserName(userId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(userId: UUID, newPassword: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun replaceAllUsers(users: List<User>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findUserByCredentials(username: String, password: String): User {
        TODO("Not yet implemented")
    }
}