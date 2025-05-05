package data.datasources.projectDataSource

import com.google.common.truth.Truth.assertThat
import data.datasources.CsvDataSource
import data.datasources.CsvDataSourceTest
import data.datasources.FileManager
import data.datasources.parser.ProjectCsvParser
import io.mockk.every
import io.mockk.mockk
import logic.entities.Project
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ExistException.ProjectAlreadyExistsException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.usecases.testFactories.CreateProjectTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.util.UUID

class ProjectCsvDataSourceTest{

    private lateinit var projectCsvDataSource: ProjectCsvDataSource
    private lateinit var fileManager: FileManager<Project>
    private lateinit var csvParser: ProjectCsvParser

    @BeforeEach
    fun setup(){
        csvParser = mockk(relaxed = true)
        fileManager = mockk(relaxed = true)
        projectCsvDataSource = ProjectCsvDataSource(fileManager, csvParser)
    }

    private fun mockDeserialization(){
        every { csvParser.deserialize(lines[0]) } returns project
        every { csvParser.deserialize(lines[1]) } returns project2
    }

    private fun mockSerialization(){
        every { csvParser.serialize(project) } returns lines[0]
        every { csvParser.serialize(project2) } returns lines[1]
    }


    // region getAllProjects
    @Test
    fun `should return list of projects when CSV file is valid`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.getAllProjects()

        // Then
        assertThat(result).isEqualTo(parsedProjects)
    }

    @Test
    fun `should return empty list when CSV file is empty`() {
        // Given
        every { fileManager.readLines() } returns emptyList()

        // When
        val result = projectCsvDataSource.getAllProjects()

        // Then
        assertThat(result).isEmpty()
    }
    // endregion

    // region getProjectById
    @Test
    fun `should return project when given valid ID`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.getProjectById(project2Id)

        // Then
        assertThat(result).isEqualTo(project2)
    }

    @Test
    fun `should throw exception when project ID does not exist`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When & Then
        assertThrows<ProjectNotFoundException>{
            projectCsvDataSource.getProjectById(notFoundId)
        }

    }
    // endregion

    // region insertProject
    @Test
    fun `should return true when project successfully inserted into CSV file`() {
        // Given
        mockSerialization()

        // When
        val result = projectCsvDataSource.insertProject(project)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project can't be inserted into CSV file`() {
        // Given
        mockSerialization()
        every { fileManager.appendLine(lines[0]) } throws ProjectAlreadyExistsException

        // When
        val result = projectCsvDataSource.insertProject(project)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region deleteProject
    @Test
    fun `should return true when project is successfully deleted`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.deleteProject(project2Id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project can't be deleted`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.deleteProject(notFoundId)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region updateProjectName
    @Test
    fun `should return true when project name is updated successfully`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.updateProjectName(project2Id, "Updated Name")

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project is not found for name update`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.updateProjectName(notFoundId, "New Name")

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region replaceAllProjects
    @Test
    fun `should return true when all projects are replaced successfully`() {
        // Given
        mockSerialization()

        // When
        val result = projectCsvDataSource.replaceAllProjects(parsedProjects)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when can't replace all`() {
        // Given
        mockSerialization()
        every { fileManager.writeLines(emptyList()) } throws EmptyDataException

        // When
        val result = projectCsvDataSource.replaceAllProjects(emptyList())

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region insertProjectState
    @Test
    fun `should return true when state is added successfully`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.insertProjectState(project2Id, "NewState")

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when project is not found for inserting state`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.insertProjectState(notFoundId, "Plan to do")

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when state is already present`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()
        val existingState = project2.states[0]

        // When
        val result = projectCsvDataSource.insertProjectState(project2Id, existingState)

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region getProjectStates
    @Test
    fun `should return list of states for project`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.getProjectStates(project2Id)

        // Then
        assertThat(result).isEqualTo(project2.states)
    }

    @Test
    fun `should return empty list when project not found`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.getProjectStates(notFoundId)

        // Then
        assertThat(result).isEmpty()
    }
    // endregion

    // region deleteProjectState
    @Test
    fun `should return true when state is removed successfully`() {
        // Given
        val state = project2.states[0]
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.deleteProjectState(project2Id, state)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when state is not present in project`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.deleteProjectState(project2Id, "one day will do")

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when project is not found for deleting state`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.deleteProjectState(notFoundId, "one day will do")

        // Then
        assertThat(result).isFalse()
    }
    // endregion

    // region updateProjectState
    @Test
    fun `should return true when project state is updated successfully`() {
        // Given
        val oldState = project2.states[0]
        val newState = "one day will do"
        every { fileManager.readLines() } returns lines
        mockDeserialization()
        every { fileManager.writeLines(any()) } returns Unit

        // When
        val result = projectCsvDataSource.updateProjectState(project2Id, oldState, newState)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when old state is not found in project`() {
        // Given
        val oldState = "one day will do"
        val newState = "maybe yes maybe no"
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.updateProjectState(project2Id,oldState ,newState )

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when project is not found for state update`() {
        // Given
        val oldState = "one day will do"
        val newState = "maybe yes maybe no"
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When
        val result = projectCsvDataSource.updateProjectState(notFoundId, oldState, newState)

        // Then
        assertThat(result).isFalse()
    }
    // endregion


    companion object{
        private val project2Id = UUID.randomUUID()
        private val notFoundId = UUID.randomUUID()
        private val project = CreateProjectTestFactory.validProjectTest
        private val project2 = project.copy(id = project2Id, name = "Project 2")

        val parsedProjects = listOf(
            project,
            project2
        )

        val lines = listOf(
            "${project.id},${project.name},${project.states[0]};${project.states[1]};${project.states[2]}",
            "${project2.id},${project2.name},${project2.states[0]};${project2.states[1]};${project2.states[2]}"
        )
    }
}