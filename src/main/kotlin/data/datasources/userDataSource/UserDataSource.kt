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
        return usersCollection.find().toList()
    }

    override suspend fun getUserById(userId: UUID): User {
        return usersCollection.find(Filters.eq("id", userId)).firstOrNull() ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertUser(user: User): Boolean {
        val existingUser = usersCollection.find(
            Filters.or(
                Filters.eq("id", user.id),
                Filters.eq("username", user.username)
            )
        ).firstOrNull()

        if (existingUser != null) return false

        return usersCollection.insertOne(user).wasAcknowledged()
    }

    override suspend fun deleteUser(userId: UUID): Boolean {
        val result = usersCollection.deleteOne(Filters.eq("id", userId))
        return result.deletedCount > 0
    }

    override suspend fun updateUserName(userId: UUID, newName: String): Boolean {
        val result = usersCollection.updateOne(
            Filters.eq("id", userId),
            Updates.set("username", newName)
        )
        return result.modifiedCount > 0
    }

    override suspend fun updatePassword(userId: UUID, newPassword: String): Boolean {
        val result = usersCollection.updateOne(
            Filters.eq("id", userId),
            Updates.set("password", newPassword)
        )
        return result.modifiedCount > 0
    }

    override suspend fun replaceAllUsers(users: List<User>): Boolean {
        val hasDuplicateId = users.map { it.id }.toSet().size != users.size
        val hasDuplicateUsernames = users.map { it.username }.toSet().size != users.size

        if (hasDuplicateId || hasDuplicateUsernames) return false

        usersCollection.deleteMany(Document())
        val result = usersCollection.insertMany(users)
        return result.wasAcknowledged() && result.insertedIds.size == users.size
    }

    override suspend fun findUserByCredentials(username: String, password: String): User {
        throw ObjectDoesNotExistException
    }
}