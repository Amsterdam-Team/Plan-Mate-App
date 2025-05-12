package data.datasources.taskDataSource

import com.google.common.truth.Truth.assertThat
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import helper.TaskFactory.validTask
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import org.bson.UuidRepresentation
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskDataSourceTest{

    @BeforeAll
    fun startMongoDB(){
        mongoClient = MongoClient.create(settings)
        database = mongoClient.getDatabase(TEST_DATABASE_NAME)
        collection = database.getCollection<Task>("tasks")
    }

    @AfterAll
    fun stopMongoDB(){
        mongoClient.close()
    }

    @BeforeEach
    fun setUp() {
        dataSource = TaskDataSource(collection)
        runBlocking {
            collection.deleteMany(Document())
            collection.insertMany(tasks)
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            collection.deleteMany(Document())
        }
    }

    // region getAllTasks
    @Test
    fun `should return all tasks when collection has tasks`() = runTest {
        // When
        val result = dataSource.getAllTasks()
        
        // Then
        assertThat(result).containsExactlyElementsIn(tasks)
    }

    @Test
    fun `should return empty list when task collection is empty`() = runTest {
        // Given
        collection.deleteMany(Document())
        
        // When
        val result = dataSource.getAllTasks()
        
        // Then
        assertThat(result).isEmpty()
    }
    // endregion

    // region getAllProjectTasks
    @Test
    fun `should return tasks for specific project`() = runTest {
        // When
        val result = dataSource.getAllTasksByProjectId(project1Id)

        // Then
        assertThat(result).containsExactly(task1Project1, task2Project1)
    }

    @Test
    fun `should return empty list when no tasks for project`() = runTest {
        // When
        val result = dataSource.getAllTasksByProjectId(UUID.randomUUID())

        // Then
        assertThat(result).isEmpty()
    }
    // endregion


    // region getTaskById
    @Test
    fun `should return task when task ID exists`() = runTest {
        // When
        val result = dataSource.getTaskById(task2Id)

        // Then
        assertThat(result).isEqualTo(task2Project1)
    }

    @Test
    fun `should throw exception when task ID does not exist`() = runTest {
        // When & Then
        assertThrows<ObjectDoesNotExistException>() {
            dataSource.getTaskById(notFoundId)
        }
    }
    // endregion


    // region insertTask
    @Test
    fun `should insert task when task is unique`() = runTest {
        // When
        val result = dataSource.insertTask(taskNotInDatabase)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should insert task when name exists in different project`() = runTest {
        // When
        val result = dataSource.insertTask(taskSameNameDifferentProject)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should not insert task when name already exists in project`() = runTest {
        // When
        val result = dataSource.insertTask(taskWithSameName)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region deleteTask
    @Test
    fun `should delete task when ID exists`() = runTest {
        // When
        val result = dataSource.deleteTaskById(task2Id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when deleting non-existent task`() = runTest {
        // When
        val result = dataSource.deleteTaskById(notFoundId)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region getTaskState
    @Test
    fun `should return task state when task exists`() = runTest {
        // When
        val result = dataSource.getTaskStateById(task2Id)

        // Then
        assertThat(result).isEqualTo(task2Project1.state)
    }

    @Test
    fun `should throw exception when task not found for get state`() = runTest {
        // When & Then
        assertThrows<ObjectDoesNotExistException> {
            dataSource.getTaskStateById(notFoundId)
        }
    }
    // endregion

    // region updateTaskName
    @Test
    fun `should update task name when new name is unique in project`() = runTest {
        // When
        val result = dataSource.updateTaskNameById(task2Id, ":D")

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should not update task name when new name exists in same project`() = runTest {
        // When
        val result = dataSource.updateTaskNameById(task2Id, task1Project1.name)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should update task name when name exists globally but not in same project`() = runTest {
        collection.insertOne(taskSameNameDifferentProjectGlobal)

        // When
        val result = dataSource.updateTaskNameById(task2Id, taskSameNameDifferentProjectGlobal.name)

        // Then
        assertThat(result).isTrue()
    }
    // endregion

    // region updateTaskState
    @Test
    fun `should update task state when task exists`() = runTest {
        // When
        val result = dataSource.updateTaskStateById(task2Id, "Finally done")

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when updating state of non-existent task`() = runTest {
        // When
        val result = dataSource.updateTaskStateById(notFoundId, "Finally done")

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    companion object{
        // Document Tasks
        private val project1Id = UUID.randomUUID()
        private val task1Project1 = validTask.copy(projectId = project1Id)
        private val task2Id = UUID.randomUUID()
        private val task2Project1 = task1Project1.copy(id = task2Id, name = "task 2 same project")
        private val project2Id = UUID.randomUUID()
        private val taskSameNameDifferentProject = task1Project1.copy(id = UUID.randomUUID(), projectId = project2Id)
        private val taskSameNameDifferentProjectGlobal = task1Project1.copy(id = UUID.randomUUID(), projectId = project2Id, name = "totally new")
        private val tasks = listOf(task1Project1, task2Project1)


        // Testing Tasks
        private val taskNotInDatabase = task1Project1.copy(id = UUID.randomUUID(), name = "task not in database")
        private val notFoundId = UUID.randomUUID()
        private val taskWithSameName = task1Project1.copy(id = UUID.randomUUID())
        private val taskWithSameId = task1Project1.copy(name = "renamed task")
        private val tasksReplace = listOf(taskWithSameId, taskWithSameName)
        private val duplicatedTaskNamesInSameProject = listOf(task1Project1, taskWithSameName)
        private val duplicatedTaskIds = listOf(task1Project1, taskWithSameId)

        // Testing Purposes
        private const val CONNECTION_STRING = "mongodb+srv://7amasa:9LlgpCLbd99zoRrJ@amsterdam.qpathz3.mongodb.net/?retryWrites=true&w=majority&appName=Amsterdam"
        private const val TEST_DATABASE_NAME = "Amsterdam-test"
        private lateinit var mongoClient: MongoClient
        private lateinit var database: MongoDatabase
        private lateinit var collection: MongoCollection<Task>
        private lateinit var dataSource: TaskDataSource
        private val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(CONNECTION_STRING))
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build()

    }
}