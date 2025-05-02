package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.entities.Task
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException

import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.project.helper.createProject
import logic.usecases.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID

class GetProjectsUseCaseTest() {

    private lateinit var repository: ProjectRepository
    private lateinit var taskRepo:TaskRepository
    private lateinit var useCase: GetProjectsUseCase


    private val projectOneId = UUID.randomUUID()
    private val projectTwoId = UUID.randomUUID()

    private val projects = listOf(
        createProject(
            id = projectOneId,
            name = "project 1",
            states = listOf("to do", "in progress"),
            tasks = emptyList()
        ),
        createProject(
            id = projectTwoId,
            name = "project 2",
            states = listOf("to do", "in progress"),
            tasks = emptyList()
        )
    )

    private val tasksForProjectOne = listOf(
        CreateTaskTestFactory.validTask.copy(id = projectOneId),
        CreateTaskTestFactory.validTask.copy(id = projectOneId),
        CreateTaskTestFactory.validTask.copy(id = projectOneId),
        CreateTaskTestFactory.validTask.copy(id = projectOneId),
    )

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        taskRepo = mockk(relaxed = true)
        useCase = GetProjectsUseCase(repository,taskRepo)
        every { repository.getProjects() } returns projects
    }

    @Test
    fun `should return correct project when project id exists in projects`() {
        val projectID = "2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        val projects = listOf(
            createProject(
                UUID.fromString(projectID),
                "PlanMate", listOf(), listOf()
            )
        )
        //Given
        every { repository.getProjects() } returns projects
        //When
        val result = useCase.getProject(projectID)
        //Then
        assertThat(result).isIn(projects)
    }

    @Test
    fun `should throw InvalidProjectIDException when projectID does not exist in Projects`() {
        val projectID ="2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        val projects = listOf(
            createProject(
                UUID.fromString("2b19ee75-2b4c-430f-bad8-dfa6b14709d0"),
                "PlanMate", listOf(), listOf()
            )
        )
        //Given
        every { repository.getProjects() } returns projects
        //When&Then
        assertThrows<ProjectNotFoundException> {
            useCase.getProject(projectID)
        }
    }

    @Test
    fun `should throw EmptyDataException when projects is empty`() {
        val projectID ="2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        //Given
        every { repository.getProjects() } returns emptyList()
        //When&Then
        assertThrows<EmptyDataException> {
            useCase.getProject(projectID)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
        " ",
        "id",
        "db373589-b656-4e68 a7c0-2ccc705ca169",
        " db373589-b656#4e68@a7c0-2ccc705ca169"
        ]
    )
    fun `should throw ProjectNotFoundException error when project ID is not a valid UUID`(invalidID:String) {
        //Given
        val projectID = invalidID

        //When & Then
        assertThrows<InvalidProjectIDException> {
            useCase.getProject(projectID)
        }

    }


    @Test
    fun `should link projectOne with matching tests when given tasks with projectOneId`(){
        // Given
        every { taskRepo.getAllTasksByProjectId(projectOneId) } returns tasksForProjectOne

        // When
        val result = useCase.getAllProjects()

        // Then
        val projectOne = result.find { it.id == projectOneId }
        assertThat(tasksForProjectOne.toSet()).isEqualTo(projectOne?.tasks?.toSet())
    }

    @Test
    fun `should link projectTwo with empty list when no tasks exist with this project id`(){
        // Given
        every { taskRepo.getAllTasksByProjectId(projectTwoId) } returns emptyList()

        // When
        val result = useCase.getAllProjects()

        // Then
        val projectTwo = result.find { it.id == projectTwoId }
        assertThat(emptyList<Task>()).isEqualTo(projectTwo?.tasks)
    }

    @Test
    fun `should return all projects even when no tasks can be linked`(){
        // When
        val result = useCase.getAllProjects()

        // Then
        val returnedIds = result.map { it.id }.toSet()
        val expectedIds = projects.map { it.id }.toSet()
        assertThat(expectedIds).isEqualTo(returnedIds)
    }

}