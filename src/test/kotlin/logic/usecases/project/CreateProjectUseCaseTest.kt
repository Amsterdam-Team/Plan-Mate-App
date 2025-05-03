import data.datasources.DataSource
import data.repository.project.ProjectRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.*
import logic.repository.ProjectRepository
import logic.usecases.project.CreateProjectUseCase
import logic.usecases.testFactories.CreateProjectTestFactory.createProject
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectStateTest
import logic.usecases.testFactories.CreateProjectTestFactory.inValidProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.validProjectTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class CreateProjectUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: CreateProjectUseCase
    private lateinit var user: User
    private lateinit var dataSource: DataSource


    @BeforeEach
    fun start() {
        dataSource = mockk()
        repository = ProjectRepositoryImpl(mockk())
        user = User(id = UUID.randomUUID(), isAdmin = true, username = "Mahmoud", password = "123456")
        useCase = CreateProjectUseCase(repository, user)
    }

    @Test
    fun `should create project when all inputs are valid`() {
        //given
        every { dataSource.getAll() } returns emptyList()
        //when and then
        assertDoesNotThrow { useCase.createProject(validProjectTest) }
    }

    @Test
    fun `should throw EmptyProjectNameException when input name is empty`() {
        //when and then
        assertThrows<EmptyProjectNameException> {
            useCase.createProject(emptyProjectNameTest)
        }
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is invalid`() {
        // when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(inValidProjectNameTest)
        }
    }

    @Test
    fun `should throw ProjectNameAlreadyExistException when there is a project added before with the same name`() {
        //given
        val existingProjects = listOf(
            createProject(name = "initial Test Project"),
            createProject(name = ""),
        )
        every { dataSource.getAll() } returns existingProjects

        // when and then
        assertThrows<ProjectNameAlreadyExistException> {
            useCase.createProject(validProjectTest)
        }
    }

    @Test
    fun `should throw EmptyProjectStateException when input states is empty`() {
        //when and then
        assertThrows<EmptyProjectStatesException> {
            useCase.createProject(emptyProjectStateTest)
        }
    }

    @Test
    fun `should throw AdminPrivilegesRequiredException when the user is not an admin`() {
        //given
        val nonAdminUser = user.copy(isAdmin = false)
        useCase = CreateProjectUseCase(repository, nonAdminUser)
        //when and then
        assertThrows<AdminPrivilegesRequiredException> {
            useCase.createProject(validProjectTest)
        }
    }
}
