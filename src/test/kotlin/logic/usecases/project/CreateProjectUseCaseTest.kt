import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException.ValidationException.*
import logic.repository.ProjectRepository
import logic.usecases.project.CreateProjectUseCase
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectStateTest
import logic.usecases.testFactories.CreateProjectTestFactory.inValidProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.validProjectTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateProjectUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: CreateProjectUseCase

    @BeforeEach
    fun start() {
        repository = mockk(relaxed = true)
        useCase = CreateProjectUseCase(repository)
    }

    @Test
    fun `should create project when all inputs are valid`() {
        //given
        verify(exactly = 1) { repository.createProject(validProjectTest) }

        //when and then
        assertThat(useCase.createProject(validProjectTest)).isTrue()
    }

    @Test
    fun `should throw EmptyProjectNameException when input name is empty`() {

        //given when and then
        assertThrows<EmptyProjectNameException> {
            useCase.createProject(emptyProjectNameTest)
        }
    }

    @Test
    fun `should throw InvalidProjectNameException when input name is invalid`() {

        //given when and then
        assertThrows<InvalidProjectNameException> {
            useCase.createProject(inValidProjectNameTest)
        }
    }

    @Test
    fun `should throw ProjectNameAlreadyExistException when there is a project added before with the same name`() {

        //given when and then
        assertThrows<ProjectNameAlreadyExistException> {
            useCase.createProject(validProjectTest)
        }
    }

    @Test
    fun `should throw EmptyProjectStateException when input states is empty`() {

        //given when and then
        assertThrows<EmptyProjectStatesException> {
            useCase.createProject(emptyProjectStateTest)
        }
    }
}
