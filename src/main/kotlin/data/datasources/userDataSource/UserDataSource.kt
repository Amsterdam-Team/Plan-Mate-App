package data.datasources.userDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.User
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import java.util.*

class UserDataSource(
    private val usersCollection: MongoCollection<User>
): IUserDataSource {

    override suspend fun getAllUsers(): List<User> {
        return usersCollection.find().toList()
    }

    override suspend fun getUserById(userId: UUID): User {
        return usersCollection.find(Filters.eq(FIELD_ID, userId)).firstOrNull() ?: throw ObjectDoesNotExistException
    }

    override suspend fun upsertUser(user: User): Boolean {
        val insertResult = usersCollection.updateOne(
            Filters.or(
                Filters.eq(FIELD_ID, user.id),
                Filters.eq(FIELD_USERNAME, user.username)
            ),
            Updates.combine(
                Updates.setOnInsert(FIELD_ID, user.id),
                Updates.setOnInsert(FIELD_USERNAME, user.username)
            ),
            UpdateOptions().upsert(true)
        )
        return insertResult.upsertedId != null
    }

    override suspend fun deleteUserById(userId: UUID): Boolean {
        val deleteResult = usersCollection.deleteOne(Filters.eq(FIELD_ID, userId))
        return deleteResult.deletedCount > 0
    }

    override suspend fun updateUserNameById(userId: UUID, newName: String): Boolean {
        val updateResult = usersCollection.updateOne(
            Filters.eq(FIELD_ID, userId),
            Updates.set(FIELD_USERNAME, newName)
        )
        return updateResult.modifiedCount > 0
    }

    override suspend fun updatePasswordById(userId: UUID, newPassword: String): Boolean {
        val updateResult = usersCollection.updateOne(
            Filters.eq(FIELD_ID, userId),
            Updates.set(FIELD_PASSWORD, newPassword)
        )
        return updateResult.modifiedCount > 0
    }


    override suspend fun findUserByCredentials(username: String, password: String): User {
        return usersCollection.find(
            Filters.and(
                Filters.eq(FIELD_USERNAME, username),
                Filters.eq(FIELD_PASSWORD, password)
            )
        ).firstOrNull() ?: throw ObjectDoesNotExistException
    }

    private companion object {
        const val FIELD_ID = "id"
        const val FIELD_USERNAME = "username"
        const val FIELD_PASSWORD = "password"
    }
}