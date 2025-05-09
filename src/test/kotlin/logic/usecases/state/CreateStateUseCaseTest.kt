package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.usecases.StateManager
import logic.usecases.ValidateInputUseCase
import org.junit.jupiter.api.assertThrows
import utils.TestDataFactory
import java.util.UUID

class CreateStateUseCaseTest {

    private lateinit var repository: ProjectRepository
    private lateinit var useCase: AddStateUseCase
    private lateinit var validateInputUseCase: ValidateInputUseCase
    private lateinit var stateManager: StateManager

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        validateInputUseCase = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        useCase = AddStateUseCase(repository,validateInputUseCase,stateManager)
    }

    @Test
    fun `should return success when state is valid and project exists`() = runTest{
        // Given
        val project = TestDataFactory.createProject()
        val newState = "In Review"
        coEvery  { repository.addStateById(project.id, newState) } returns  true

        // When
        val result = useCase.execute(project.id.toString(), newState)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return error when state name is blank`()  = runTest{
        // Given
        val project = TestDataFactory.createProject()
        val blankState = "  "

        // When&Then
        assertThrows <InvalidStateNameException>{ useCase.execute(project.id.toString(), blankState) }
    }

    @Test
    fun `should return error when project does not exist`() =runTest{
        // Given
        val fakeProjectId = UUID.randomUUID()
        val state = "Done"
        coEvery {
            repository.addStateById(
                fakeProjectId,
                state
            )
        } throws ProjectNotFoundException

        // When&Then
        assertThrows <ProjectNotFoundException>{ useCase.execute(fakeProjectId.toString(), state) }
    }

    @Test
    fun `should return error when state name already exists in the project`() = runTest{
        // Given
        val project = TestDataFactory.createProject(states = listOf("TODO", "In Review"))
        val duplicateState = "todo"
        coEvery { repository.getProject(project.id) } returns project

        // when&Then
        assertThrows <SameStateNameException>{ useCase.execute(project.id.toString(), duplicateState) }
    }

    @Test
    fun `should return error when state name with spaces already exists in the project`() = runTest{
        // Given
        val project = TestDataFactory.createProject(states = listOf("In Progress", "Done"))
        val duplicateWithSpaces = "  in progress  "
        coEvery { repository.getProject(project.id) } returns project

        // When&Then
        assertThrows <SameStateNameException>{ useCase.execute(project.id.toString(), duplicateWithSpaces) }
    }
}