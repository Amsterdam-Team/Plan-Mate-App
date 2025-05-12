package data.datasources.logDataSource

import com.google.common.truth.Truth.assertThat
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import helper.LogFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import logic.entities.LogItem
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import org.bson.UuidRepresentation
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LogDataSourceTest{

    @BeforeAll
    fun startMongoDB(){
        mongoClient = MongoClient.create(settings)
        database = mongoClient.getDatabase(TEST_DATABASE_NAME)
        collection = database.getCollection<LogItem>("logs")
    }

    @AfterAll
    fun stopMongoDB(){
        mongoClient.close()
    }

    @BeforeEach
    fun setUp() {
        dataSource = LogDataSource(collection)
        runBlocking {
            collection.deleteMany(Document())
            collection.insertMany(logs)
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            collection.deleteMany(Document())
        }
    }


    // region getAllLogs
    @Test
    fun `should return list of logs when collection has logs`() = runTest {
        // When
        val result = dataSource.getAllLogs()

        // Then
        assertThat(result).isEqualTo(logs)
    }

    @Test
    fun `should return empty list when collection has no logs`() = runTest {
        // Given
        collection.deleteMany(Document())

        // When
        val result = dataSource.getAllLogs()

        // Then
        assertThat(result).isEmpty()
    }
    // endregion

    // region getLogsByEntityId
    @Test
    fun `should return logs related to given entityId`() = runTest {
        // When
        val result = dataSource.getLogsByEntityId(entityId2)

        // Then
        assertThat(result).containsExactly(log2, log2SameEntity)
    }

    @Test
    fun `should return empty list when no logs exist for given entityId`() = runTest {
        // When
        val result = dataSource.getLogsByEntityId(notFoundId)

        // Then
        assertThat(result).isEmpty()
    }
    // endregion

    // region getLogById
    @Test
    fun `should return log when given valid logId`() = runTest {
        // When
        val result = dataSource.getLogById(log2Id)

        // Then
        assertThat(result).isEqualTo(log2)
    }

    @Test
    fun `should throw exception when logId does not exist`() = runTest {
        // When & Then
        assertThrows<ObjectDoesNotExistException> {
            dataSource.getLogById(notFoundId)
        }
    }
    // endregion

    // region insertLog
    @Test
    fun `should return true when log is inserted successfully`() = runTest {
        // When
        val result = dataSource.insertLog(logNotInDatabase)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when inserting log with existing id fails`() = runTest {
        // When
        val result = dataSource.insertLog(logWithSameId)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region deleteLog
    @Test
    fun `should return true when log is deleted successfully`() = runTest {
        // When
        val result = dataSource.deleteLog(log2Id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when log to delete is not found`() = runTest {
        // When
        val result = dataSource.deleteLog(notFoundId)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    companion object{
        // Document Logs
        private val log1 = LogFactory.createLogItem()
        private val log2Id = UUID.randomUUID()
        private val entityId2 = UUID.randomUUID()
        private val log2 = log1.copy(id = log2Id, message = "log 2 message", entityId = entityId2)
        private val log2SameEntity = log2.copy(id = UUID.randomUUID(), message = "log 2 message 2")
        private val logs = listOf(log1, log2, log2SameEntity)

        // Testing Users
        private val logNotInDatabase = log1.copy(id = UUID.randomUUID(), message = "yooooo")
        private val notFoundId = UUID.randomUUID()
        private val logWithSameId = log1.copy(message = "luigi")

        // Testing Purposes
        private const val CONNECTION_STRING = "mongodb+srv://7amasa:9LlgpCLbd99zoRrJ@amsterdam.qpathz3.mongodb.net/?retryWrites=true&w=majority&appName=Amsterdam"
        private const val TEST_DATABASE_NAME = "Amsterdam-test"
        private lateinit var mongoClient: MongoClient
        private lateinit var database: MongoDatabase
        private lateinit var collection: MongoCollection<LogItem>
        private lateinit var dataSource: LogDataSource
        private val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(CONNECTION_STRING))
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build()
    }
}