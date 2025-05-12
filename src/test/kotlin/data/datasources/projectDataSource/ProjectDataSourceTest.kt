package data.datasources.projectDataSource

import com.google.common.truth.Truth.assertThat
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import helper.ProjectFactory.createProject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import logic.entities.Project
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import org.bson.UuidRepresentation
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectDataSourceTest {


    @BeforeAll
    fun startMongoDB() {
        mongoClient = MongoClient.create(settings)
        database = mongoClient.getDatabase(TEST_DATABASE_NAME)
        collection = database.getCollection<Project>("projects")
    }

    @AfterAll
    fun stopMongoDB() {
        mongoClient.close()
    }

    @BeforeEach
    fun setUp() {
        dataSource = ProjectDataSource(collection)
        runBlocking {
            collection.deleteMany(Document())
            collection.insertMany(projects)
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            collection.deleteMany(Document())
        }
    }


    // region getAllProjects
    @Test
    fun `should return list of projects when collection has projects`() = runTest {
        // When
        val result = dataSource.getAllProjects()

        // Then
        assertThat(result).isEqualTo(projects)
    }


    @Test
    fun `should return empty list when collection has no projects`() = runTest {
        // Given
        collection.deleteMany(Document())

        // When
        val result = dataSource.getAllProjects()

        // Then
        assertThat(result).isEmpty()
    }

    // endregion

    // region getProjectById
    @Test
    fun `should return project when given valid ID`() = runTest {
        // When
        val result = dataSource.getProjectById(project2Id)

        // Then
        assertThat(result).isEqualTo(project2)
    }


    @Test
    fun `should throw exception when project ID does not exist`() = runTest {
        // When & Then
        assertThrows<ObjectDoesNotExistException> {
            dataSource.getProjectById(notFoundId)
        }
    }
    // endregion

    // region insertProject
    @Test
    fun `should return true when project is inserted successfully`() = runTest {
        // When
        val result = dataSource.insertProject(projectNotInDatabase)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when inserting project with existing id fails`() = runTest {
        // When
        val result = dataSource.insertProject(projectWithSameId)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when inserting project with existing name fails`() = runTest {
        // When
        val result = dataSource.insertProject(projectWithSameName)

        // Then
        assertThat(result).isFalse()
    }

    // endregion

    // region deleteProject
    @Test
    fun `should return true when project is deleted successfully`() = runTest {
        // When
        val result = dataSource.deleteProject(project2Id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project to delete is not found`() = runTest {
        // When
        val result = dataSource.deleteProject(notFoundId)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region updateProjectName
    @Test
    fun `should return true when project name is updated successfully`() = runTest {
        // When
        val result = dataSource.updateProjectName(project2Id, "New Name")

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project is not found for name update`() = runTest {
        // When
        val result = dataSource.updateProjectName(notFoundId, "New Name")

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region insertProjectState
    @Test
    fun `should return true when state is inserted successfully`() = runTest {
        // When
        val result = dataSource.insertProjectState(project2Id, "Very very important")

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project ID for inserting state does not exist`() = runTest {
        // When
        val result = dataSource.insertProjectState(notFoundId, "Very very important")

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when state already exists`() = runTest {
        // When
        val result = dataSource.insertProjectState(project2Id, project2.states.first())

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region getProjectStates
    @Test
    fun `should return list of states when project exists`() = runTest {
        // When
        val result = dataSource.getProjectStates(project2Id)

        // Then
        assertThat(result).containsExactlyElementsIn(project2.states)
    }

    @Test
    fun `should return empty list when project ID for getting states does not exist`() = runTest {
        // When
        val result = dataSource.getProjectStates(notFoundId)

        // Then
        assertThat(result).isEmpty()
    }
    // endregion

    // region deleteProjectState
    @Test
    fun `should return true when state is deleted successfully`() = runTest {
        // When
        val result = dataSource.deleteProjectState(project2Id, project2.states.first())

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project ID for deleting state does not exist`() = runTest {
        // When
        val result = dataSource.deleteProjectState(notFoundId, "To Do")

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when state does not exist`() = runTest {
        // When
        val result = dataSource.deleteProjectState(project2Id, "this state is not real")

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region updateProjectState
    @Test
    fun `should return true when state is updated successfully`() = runTest {
        // When
        val result = dataSource.updateProjectState(project2Id, project2.states.first(), "good!!!")

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project ID does not exist`() = runTest {
        // When
        val result = dataSource.updateProjectState(notFoundId, "To Do", "bad :(")

        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when old state does not exist`() = runTest {
        // When
        val result = dataSource.updateProjectState(project2Id, "business business", ":(")

        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when new state already exists`() = runTest {
        // When
        val result = dataSource.updateProjectState(
            project2Id, project2.states.first(),
            project2.states.last()
        )

        assertThat(result).isFalse()
    }
    // endregion

    companion object {
        // Document Projects
        private val project1 = createProject()
        private val project2Id = UUID.randomUUID()
        private val project2 = project1.copy(
            id = project2Id,
            name = "amsterdam",
            states = listOf("To Do", "In Progress", "Done")
        )
        private val projects = listOf(project1, project2)

        // Testing Projects
        private val projectNotInDatabase = project1.copy(id = UUID.randomUUID(), name = "projects")
        private val notFoundId = UUID.randomUUID()
        private val projectWithSameName = project1.copy(id = UUID.randomUUID())
        private val projectWithSameId = project1.copy(name = "very important")
        private val projectsReplace = listOf(projectWithSameId, projectWithSameName)
        private val duplicatedProjectNames = listOf(project1, projectWithSameName)
        private val duplicatedProjectIds = listOf(project1, projectWithSameId)


        // Testing Purposes
        private const val CONNECTION_STRING =
            "mongodb+srv://7amasa:9LlgpCLbd99zoRrJ@amsterdam.qpathz3.mongodb.net/?retryWrites=true&w=majority&appName=Amsterdam"
        private const val TEST_DATABASE_NAME = "Amsterdam-test"
        private lateinit var mongoClient: MongoClient
        private lateinit var database: MongoDatabase
        private lateinit var collection: MongoCollection<Project>
        private lateinit var dataSource: ProjectDataSource
        private val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(CONNECTION_STRING))
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build()

    }
}