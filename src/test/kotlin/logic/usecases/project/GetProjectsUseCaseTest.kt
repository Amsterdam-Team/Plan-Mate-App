package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import helper.ProjectFactory.createProject
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidUUIDFormatException
import logic.repository.IProjectRepository
import logic.usecases.utils.ValidateInputUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID

class GetProjectsUseCaseTest() {

    private lateinit var repository: IProjectRepository
    private lateinit var tasksUseCase: GetAllTasksByProjectIdUseCase
    private lateinit var validationUseCase:ValidateInputUseCase
    private lateinit var useCase: GetProjectDetailsUseCase


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


    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        tasksUseCase = mockk(relaxed = true)
        validationUseCase = mockk(relaxed = true)
        useCase = GetProjectDetailsUseCase(repository,tasksUseCase,validationUseCase)
    }

    @Test
    fun `should return correct project when project id exists in projects`() = runTest {
        //Given
        coEvery { repository.getProjectById(projectOneId)} returns projects[0]
        every { validationUseCase.isValidUUID(projectOneId.toString()) } returns true
        //When
        val result = useCase(projectOneId.toString())
        //Then
        assertThat(result).isIn(projects)
    }

    @Test
    fun `should throw ProjectNotFoundException when projectID does not exist in Projects`() = runTest{
        //Given
        coEvery { repository.getProjectById(UUID.fromString("db373589-b656-4e68-a7c0-2ccc705ca169")) } throws ProjectNotFoundException
        every { validationUseCase.isValidUUID("db373589-b656-4e68-a7c0-2ccc705ca169") } returns true

        //When&Then
        assertThrows<ProjectNotFoundException> {
            useCase("db373589-b656-4e68-a7c0-2ccc705ca169")
        }
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
        " ",
        "id",
        "db373589-b656-4e68  a7c0-2ccc705ca169",
        " db373589-b656#4e68@a7c0-2ccc705ca169"
        ]
    )
    fun `should throw InvalidUUIDFormatException error when project ID is not a valid UUID`(invalidID:String) = runTest{
        //Given
        val projectID = invalidID

        //When & Then
        assertThrows<InvalidUUIDFormatException> {
            useCase(projectID)
        }

    }




}