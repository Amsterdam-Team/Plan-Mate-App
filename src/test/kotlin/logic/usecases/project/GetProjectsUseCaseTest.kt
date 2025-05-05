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
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import logic.usecases.task.GetTaskByIdUseCase
import logic.usecases.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID

class GetProjectsUseCaseTest() {

    private lateinit var repository: ProjectRepository
    private lateinit var tasksUseCase: GetAllTasksByProjectIdUseCase
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
        tasksUseCase = mockk(relaxed = true)
        useCase = GetProjectsUseCase(repository,tasksUseCase)
        every { repository.getProjects() } returns projects
    }

    @Test
    fun `should return correct project when project id exists in projects`() {
        //Given
        every { repository.getProject(projectOneId)} returns projects[0]
        //When
        val result = useCase.getProject(projectOneId.toString())
        //Then
        assertThat(result).isIn(projects)
    }

    @Test
    fun `should throw ProjectNotFoundException when projectID does not exist in Projects`() {
        //Given
        every { repository.getProject(projectOneId) } returns projects[0]
        //When&Then
        assertThrows<ProjectNotFoundException> {
            useCase.getProject("2b19ee75-2b4c-430f-bad8-dfa6b14709d9")
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
    fun `should throw InvalidProjectIDException error when project ID is not a valid UUID`(invalidID:String) {
        //Given
        val projectID = invalidID

        //When & Then
        assertThrows<InvalidProjectIDException> {
            useCase.getProject(projectID)
        }

    }




}