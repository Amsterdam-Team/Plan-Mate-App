package data.datasources.userDataSource

import com.google.common.truth.Truth.assertThat
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import logic.entities.User
import logic.exception.PlanMateException
import org.bson.Document
import org.bson.UuidRepresentation
import org.junit.jupiter.api.*
import utils.TestDataFactory
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDataSourceTest {

    private lateinit var mongoClient: MongoClient
    private lateinit var database: MongoDatabase
    private lateinit var collection: MongoCollection<User>
    private lateinit var dataSource: UserDataSource



    @BeforeEach
    fun setUp() {
        mongoClient = MongoClient.create(settings)
        database = mongoClient.getDatabase(TEST_DATABASE_NAME)
        collection = database.getCollection<User>("users")
        dataSource = UserDataSource(collection)

        runBlocking {
            collection.deleteMany(Document())
            collection.insertMany(users)
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            collection.deleteMany(Document())
        }
        mongoClient.close()
    }

    // region getAllUsers
    @Test
    fun `should return list of users when collection has users`() = runTest {
        // When
        val result = dataSource.getAllUsers()

        // Then
        assertThat(result).isEqualTo(users)
    }


    @Test
    fun `should return empty list when collection has no users`() = runTest {
        // When
        val result = dataSource.getAllUsers()

        // Then
        assertThat(result).isEmpty()
    }

    // endregion

    // region getUserById
    @Test
    fun `should return user when given valid ID`() = runTest {
        // When
        val result = dataSource.getUserById(user2Id)

        // Then
        assertThat(result).isEqualTo(user2)
    }


    @Test
    fun `should throw exception when user ID does not exist`() = runTest {
        // When & Then
        assertThrows<PlanMateException.DataSourceException.ObjectDoesNotExistException> {
            dataSource.getUserById(notFoundId)
        }
    }
    // endregion

    // region insertUser
    @Test
    fun `should return true when user is inserted successfully`() = runTest {
        // When
        val result = dataSource.insertUser(userNotInDatabase)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when inserting user with existing id fails`() = runTest {
        // When
        val result = dataSource.insertUser(userWithSameId)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when inserting user with existing username fails`() = runTest {
        // When
        val result = dataSource.insertUser(userWithSameName)

        // Then
        assertThat(result).isFalse()
    }

    // endregion

    // region deleteUser
    @Test
    fun `should return true when user is deleted successfully`() = runTest {
        // When
        val result = dataSource.deleteUser(user2Id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when user to delete is not found`() = runTest {
        // When
        val result = dataSource.deleteUser(notFoundId)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region updateUserName
    @Test
    fun `should return true when username is updated successfully`() {
        // Given

        // When

        // Then
    }

    @Test
    fun `should return false when user is not found for username update`() {
        // Given

        // When

        // Then
    }
    // endregion

    // region updatePassword
    @Test
    fun `should return true when password is updated successfully`() {
        // Given

        // When

        // Then
    }

    @Test
    fun `should return false when user is not found for password update`() {
        // Given

        // When

        // Then
    }
    // endregion

    // region replaceAllUsers
    @Test
    fun `should return true when all users are replaced successfully`() {
        // Given

        // When

        // Then
    }

    @Test
    fun `should return false when replace operation fails`() {
        // Given

        // When

        // Then
    }
    // endregion

    // region findUserByCredentials
    @Test
    fun `should return user when credentials match`() = runTest {
        // Given

        // When

        // Then
    }

    @Test
    fun `should throw exception when credentials do not match any user`() {
        // Given

        // When & Then
    }
    // endregion

    companion object {
        // Document Users
        private val user1 = TestDataFactory.createUser()
        private val user2Id = UUID.randomUUID()
        private val user2 = user1.copy(id = user2Id, username = "me")
        private val users = listOf(user1, user2)

        // Testing Users
        private val userNotInDatabase = user1.copy(id = UUID.randomUUID(), username = "7amasa")
        private val notFoundId = UUID.randomUUID()
        private val userWithSameName = user1.copy(id = UUID.randomUUID())
        private val userWithSameId = user1.copy(username = "luigi")

        private const val CONNECTION_STRING = "mongodb+srv://7amasa:9LlgpCLbd99zoRrJ@amsterdam.qpathz3.mongodb.net/?retryWrites=true&w=majority&appName=Amsterdam"
        private const val TEST_DATABASE_NAME = "Amsterdam-test"
        private val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(CONNECTION_STRING))
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build()

    }
}