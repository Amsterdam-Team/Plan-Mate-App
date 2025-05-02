package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException

import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID

class GetProjectUseCaseTest() {

    private lateinit var repository: ProjectRepository
    private lateinit var taskRepo:TaskRepository
    private lateinit var usecase: GetProjectUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        taskRepo = mockk(relaxed = true)
        usecase = GetProjectUseCase(repository,taskRepo)
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
        val result = usecase.getProject(projectID)
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
            usecase.getProject(projectID)
        }
    }

    @Test
    fun `should throw EmptyDataException when projects is empty`() {
        val projectID ="2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        //Given
        every { repository.getProjects() } returns emptyList()
        //When&Then
        assertThrows<EmptyDataException> {
            usecase.getProject(projectID)
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
            usecase.getProject(projectID)
        }

    }

}