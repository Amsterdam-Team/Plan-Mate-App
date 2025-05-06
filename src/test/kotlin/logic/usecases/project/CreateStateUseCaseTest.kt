package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.ResultStatus
import utils.TestDataFactory.createProject
import java.util.UUID

class CreateStateUseCaseTest {

    private lateinit var repository: ProjectRepository
    private lateinit var useCase: CreateStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = CreateStateUseCase(repository)
    }

    @Test
    fun `should return success when state is valid and project exists`() {
        // Given
        val project = createProject()
        val newState = "In Review"
        every { repository.addStateById(project.id, newState) } just Runs

        // When
        val result = useCase.execute(project.id, newState)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Success::class.java)
    }

    @Test
    fun `should return error when state name is blank`() {
        // Given
        val project = createProject()
        val blankState = "  "

        // When
        val result = useCase.execute(project.id, blankState)

        // Then
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(InvalidStateNameException::class.java)
    }

    @Test
    fun `should return error when project does not exist`() {
        // Given
        val fakeProjectId = UUID.randomUUID()
        val state = "Done"
        every { repository.addStateById(fakeProjectId, state) } throws ProjectNotFoundException

        // When
        val result = useCase.execute(fakeProjectId, state)

        // Then
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(ProjectNotFoundException::class.java)
    }

    @Test
    fun `should return error when state name already exists in the project`() {
        // Given
        val project = createProject(states = listOf("TODO", "In Review"))
        val duplicateState = "todo"
        every { repository.getProject(project.id) } returns project

        // When
        val result = useCase.execute(project.id, duplicateState)

        // Then
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(SameStateNameException::class.java)
    }

    @Test
    fun `should return error when state name with spaces already exists in the project`() {
        // Given
        val project = createProject(states = listOf("In Progress", "Done"))
        val duplicateWithSpaces = "  in progress  "
        every { repository.getProject(project.id) } returns project

        // When
        val result = useCase.execute(project.id, duplicateWithSpaces)

        // Then
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(SameStateNameException::class.java)
    }
}