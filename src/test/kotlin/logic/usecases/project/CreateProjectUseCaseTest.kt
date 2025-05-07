import com.google.common.truth.Truth.assertThat
import data.datasources.projectDataSource.ProjectDataSourceInterface
import data.repository.project.ProjectRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.*
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.usecases.ValidateInputUseCase
import logic.usecases.project.CreateProjectUseCase
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
    private lateinit var user: User
    private lateinit var projectDataSource: ProjectDataSourceInterface
    private lateinit var logRepository: LogRepository
    private val validateInputUseCase = ValidateInputUseCase()


    @BeforeEach
    fun start() {
        projectDataSource = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        repository = ProjectRepositoryImpl(projectDataSource, logRepository)
        user = User(id = UUID.randomUUID(), isAdmin = true, username = "Mahmoud", password = "123456")
        useCase = CreateProjectUseCase(repository, user, validateInputUseCase)
    }

    @Test
    fun `should create project when all inputs are valid`() {
        //given
        every { projectDataSource.getAllProjects() } returns emptyList()

        //when
        val createProject = useCase.createProject(validProjectTest.name, validProjectTest.states)

        //then
        assertThat(createProject).isTrue()
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is empty`() {
        //when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(emptyProjectNameTest.name, emptyProjectNameTest.states)
        }
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is invalid`() {
        // when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(inValidProjectNameTest.name, inValidProjectNameTest.states)
        }
    }

    @Test
    fun `should throw ProjectNameAlreadyExistException when there is a project added before with the same name`() {
        //given
        val existingProjects = listOf(
            createProject(name = "initial Test Project"),
            createProject(name = ""),
        )
        every { projectDataSource.getAllProjects() } returns existingProjects

        // when and then
        assertThrows<ProjectNameAlreadyExistException> {
            useCase.createProject(validProjectTest.name, validProjectTest.states)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when input states is empty`() {
        //when and then
        assertThrows<InvalidStateNameException> {
            useCase.createProject(emptyProjectStateTest.name, emptyProjectStateTest.states)
        }
    }

    @Test
    fun `should throw AdminPrivilegesRequiredException when the user is not an admin`() {
        //given
        val nonAdminUser = user.copy(isAdmin = false)
        useCase = CreateProjectUseCase(repository, nonAdminUser, validateInputUseCase)
        //when and then
        assertThrows<AdminPrivilegesRequiredException> {
            useCase.createProject(validProjectTest.name, validProjectTest.states)
        }
    }
}
