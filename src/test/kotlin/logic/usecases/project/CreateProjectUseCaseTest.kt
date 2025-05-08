package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.*
import logic.repository.ProjectRepository
import logic.usecases.LoggerUseCase
import logic.usecases.StateManager
import logic.usecases.ValidateInputUseCase
import logic.usecases.testFactories.CreateProjectTestFactory.createProject
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectStateTest
import logic.usecases.testFactories.CreateProjectTestFactory.inValidProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.validProjectTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CreateProjectUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: CreateProjectUseCase
    private lateinit var loggerUseCase: LoggerUseCase
    private lateinit var stateManager: StateManager
    private lateinit var validateInputUseCase : ValidateInputUseCase


    @BeforeEach
    fun start() {
        repository = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        useCase = CreateProjectUseCase(repository, stateManager, validateInputUseCase,loggerUseCase)
    }

    @Test
    fun `should create project when all inputs are valid`() = runTest{
        //given
        coEvery { repository.getProjects() } returns emptyList()

        //when
        val createProject = useCase.createProject(validProjectTest.name, validProjectTest.states)

        //then
        assertThat(createProject).isTrue()
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is empty`() = runTest{
        //when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(emptyProjectNameTest.name, emptyProjectNameTest.states)
        }
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is invalid`() =runTest{
        // when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(inValidProjectNameTest.name, inValidProjectNameTest.states)
        }
    }

    @Test
    fun `should throw ProjectNameAlreadyExistException when there is a project added before with the same name`() =runTest{
        //given
        val existingProjects = listOf(
            createProject(name = "initial Test Project"),
            createProject(name = ""),
        )
        coEvery { repository.getProjects() } returns existingProjects

        // when and then
        assertThrows<ProjectNameAlreadyExistException> {
            useCase.createProject(validProjectTest.name, validProjectTest.states)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when input states is empty`() =runTest{
        //when and then
        assertThrows<InvalidStateNameException> {
            useCase.createProject(emptyProjectStateTest.name, emptyProjectStateTest.states)
        }
    }

    @Test
    fun `should throw AdminPrivilegesRequiredException when the user is not an admin`() = runTest{
        //given
        coEvery { stateManager.getLoggedInUser() } returns User(UUID.randomUUID(),false,"mahmoud","112233")
        //when and then
        assertThrows<AdminPrivilegesRequiredException> {
            useCase.createProject(validProjectTest.name, validProjectTest.states)
        }
    }
}
