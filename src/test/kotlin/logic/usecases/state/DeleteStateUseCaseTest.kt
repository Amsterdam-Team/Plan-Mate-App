package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.Project
import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.IProjectRepository
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*


class DeleteStateUseCaseTest {
    private lateinit var repository: IProjectRepository
    private lateinit var taskRepository: ITaskRepository
    private lateinit var useCase: DeleteStateUseCase
    lateinit var stateManager: StateManager
    lateinit var loggerUseCase: LoggerUseCase
    lateinit var validateInputUseCase: ValidateInputUseCase


    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        taskRepository = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        validateInputUseCase = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        useCase =
            DeleteStateUseCase(
                repository,
                taskRepository,
                stateManager,
                loggerUseCase,
                validateInputUseCase,

                )

        coEvery {
            stateManager.getLoggedInUser()
        } returns User(
            id = UUID.randomUUID(),
            username = "admin",
            password = "pass",
            isAdmin = true
        )
    }

    @Test
    fun `deleteStateById should delete state by state id and project id from repository when called`() = runTest {
        // Given
        val state = "state"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val project = mockk<logic.entities.Project>(relaxed = true)

        coEvery { validateInputUseCase.isValidName(state) } returns true
        coEvery { validateInputUseCase.isValidUUID(projectId.toString()) } returns true
        coEvery { repository.getProjectById(projectId) } returns project
        every { project.states } returns listOf(state)
        coEvery { taskRepository.hasTasksWithState(projectId, state) } returns false
        coEvery { repository.deleteStateById(projectId, state) } returns true

        // When
        useCase(projectId = projectId.toString(), state = state)

        // Then
        coVerify(exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = state) }
    }

    @Test
    fun `delete use case should return true when state is successfully deleted`() = runTest {
        val oldState = "oldState"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val project = mockk<Project>(relaxed = true)

        coEvery { validateInputUseCase.isValidName(oldState) } returns true
        coEvery { validateInputUseCase.isValidUUID(projectId.toString()) } returns true
        coEvery { repository.getProjectById(projectId) } returns project
        coEvery { project.states } returns listOf(oldState)
        coEvery { taskRepository.hasTasksWithState(projectId, oldState) } returns false
        coEvery { repository.deleteStateById(projectId, oldState) } returns true

        val result = useCase(projectId = projectId.toString(), state = oldState)

        assertThat(result).isTrue()
    }

    @Test
    fun `delete use case should return false when state is failed deleted`() = runTest {
        val oldState = "oldState"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val project = mockk<logic.entities.Project>(relaxed = true)

        coEvery { validateInputUseCase.isValidName(oldState) } returns true
        coEvery { validateInputUseCase.isValidUUID(projectId.toString()) } returns true
        coEvery { repository.getProjectById(projectId) } returns project
        every { project.states } returns listOf(oldState)
        coEvery { taskRepository.hasTasksWithState(projectId, oldState) } returns false
        coEvery { repository.deleteStateById(projectId, oldState) } returns false

        val result = useCase(projectId = projectId.toString(), state = oldState)

        assertThat(result).isFalse()
    }


    @Test
    fun `should throw AdminPrivilegesRequiredException when user is not admin`() = runTest {
        coEvery { stateManager.getLoggedInUser() } returns User(UUID.randomUUID(), "user", "pass", isAdmin = false)

        assertThrows<PlanMateException.AuthorizationException.AdminPrivilegesRequiredException> {
            useCase(projectId = "00000000-0000-0000-0000-000000000001", state = "state")
        }
    }


    @Test
    fun `should throw StateNotFoundException when state is not in project states`() = runTest {
        val state = "missingState"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val project = mockk<logic.entities.Project>(relaxed = true)

        coEvery { validateInputUseCase.isValidUUID(any()) } returns true
        coEvery { validateInputUseCase.isValidName(any()) } returns true
        coEvery { repository.getProjectById(projectId) } returns project
        every { project.states } returns listOf("existingState")

        assertThrows<PlanMateException.NotFoundException.StateNotFoundException> {
            useCase(projectId = projectId.toString(), state = state)
        }
    }

    @Test
    fun `should throw StateHasTasksException when state has associated tasks`() = runTest {
        val state = "state"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val project = mockk<logic.entities.Project>(relaxed = true)

        coEvery { validateInputUseCase.isValidUUID(any()) } returns true
        coEvery { validateInputUseCase.isValidName(any()) } returns true
        coEvery { repository.getProjectById(projectId) } returns project
        every { project.states } returns listOf(state)
        coEvery { taskRepository.hasTasksWithState(projectId, state) } returns true

        assertThrows<PlanMateException.ValidationException.StateHasTasksException> {
            useCase(projectId = projectId.toString(), state = state)
        }
    }

}
