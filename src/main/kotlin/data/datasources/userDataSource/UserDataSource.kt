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
        return emptyList()
    }

    override suspend fun getUserById(userId: UUID): User {
        throw ObjectDoesNotExistException
    }

    override suspend fun insertUser(user: User): Boolean {
        return false
    }

    override suspend fun deleteUser(userId: UUID): Boolean {
        return false
    }

    override suspend fun updateUserName(userId: UUID, newName: String): Boolean {
        return false
    }

    override suspend fun updatePassword(userId: UUID, newPassword: String): Boolean {
        return false
    }

    override suspend fun replaceAllUsers(users: List<User>): Boolean {
        return false
    }

    override suspend fun findUserByCredentials(username: String, password: String): User {
        throw ObjectDoesNotExistException
    }
}