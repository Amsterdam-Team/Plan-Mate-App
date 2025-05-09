package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import helper.ProjectFactory.emptyProjectNameTest
import helper.ProjectFactory.emptyProjectStateTest
import helper.ProjectFactory.inValidProjectNameTest
import helper.ProjectFactory.validProjectTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CreateProjectUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: CreateProjectUseCase
    private lateinit var loggerUseCase: LoggerUseCase
    private lateinit var stateManager: StateManager
    private lateinit var validateInputUseCase: ValidateInputUseCase


    @BeforeEach
    fun start() {
        repository = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        validateInputUseCase = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        useCase =
            CreateProjectUseCase(repository, stateManager, validateInputUseCase, loggerUseCase)
        coEvery { stateManager.getLoggedInUser() } returns User(
            UUID.randomUUID(),
            "admin",
            "pass",
            true
        )
    }

    @Test
    fun `should create project when all inputs are valid`() = runTest {
        //given
        coEvery { repository.getProjects() } returns emptyList()
        coEvery { validateInputUseCase.isValidName(validProjectTest.name) } returns true
        coEvery { validateInputUseCase.isValidProjectStates(validProjectTest.states) } returns true
        coEvery { repository.createProject(any()) } returns true

        //when
        val result = useCase.createProject(validProjectTest.name, validProjectTest.states)

        //then
        assertThat(result).isTrue()
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is empty`() = runTest {
        //when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(emptyProjectNameTest.name, emptyProjectNameTest.states)
        }
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is invalid`() = runTest {
        // when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(inValidProjectNameTest.name, inValidProjectNameTest.states)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when input states is empty`() = runTest {
        // Given
        coEvery { validateInputUseCase.isValidName(emptyProjectStateTest.name) } returns true
        coEvery { validateInputUseCase.isValidProjectStates(emptyProjectStateTest.states) } returns false

        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.createProject(emptyProjectStateTest.name, emptyProjectStateTest.states)
        }
    }

    @Test
    fun `should throw AdminPrivilegesRequiredException when the user is not an admin`() = runTest {
        //given
        coEvery { stateManager.getLoggedInUser() } returns User(
            UUID.randomUUID(),
            "mahmoud",
            "112233",
            false
        )
        //when and then
        assertThrows<AdminPrivilegesRequiredException> {
            useCase.createProject(validProjectTest.name, validProjectTest.states)
        }
    }
}
