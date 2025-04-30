import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.ValidationException.ProjectNameAlreadyExistException
import logic.repository.ProjectRepository
import logic.usecases.project.CreateProjectUseCase
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
        every { repository.createProject(validProjectTest) } returns Unit

        //when and then
        assertThat(useCase.createProject(validProjectTest)).isTrue()
    }

    @Test
    fun `should throw ProjectNameAlreadyExistException when there is a project added before with the same name`() {
        //given
        every { repository.createProject(validProjectTest) } throws ProjectNameAlreadyExistException

        //when and then
        assertThrows<ProjectNameAlreadyExistException> {
            useCase.createProject(validProjectTest)
        }
    }
}