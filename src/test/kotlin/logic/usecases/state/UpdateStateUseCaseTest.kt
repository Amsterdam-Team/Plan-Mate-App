package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.User
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.repository.ProjectRepository
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID
import kotlin.test.Test

class UpdateStateUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var validationUseCase: ValidateInputUseCase
    private lateinit var useCase: UpdateStateUseCase
    private lateinit var user: User
    private lateinit var stateManager: StateManager

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        validationUseCase = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        user =
            User(id = UUID.randomUUID(), isAdmin = true, username = "Shrouk", password = "123456")
        useCase = UpdateStateUseCase(repository, stateManager, validationUseCase)
        every { stateManager.getLoggedInUser() } returns user
        every { validationUseCase.isValidUUID(any()) } returns true
        every { validationUseCase.isValidName(any()) } returns true
        every { validationUseCase.areIdentical(any(), any()) } returns false

    }

    @Test
    fun `should update state name when inputs are valid`() = runTest {
        // Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "In Progress"
        val newState = "In Review"
        coEvery {
            repository.updateProjectStateById(
                UUID.fromString(projectID),
                oldState,
                newState
            )
        } returns true

        // When
        val result = useCase.updateState(projectID, oldState, newState)

        // Then
        assertThat(result).isTrue()

    }

    @Test
    fun `should throw AdminPrivilegesRequiredException when user is not admin`() = runTest {
        // Given
        val projectId = "db373589-b656-4e68-a7c1-2ccc705ca169"
        val oldState = "Done"
        val newState = "Finished"

        every {
            stateManager.getLoggedInUser()
        } returns User(
            id = UUID.randomUUID(),
            username = "shrouk",
            password = "123456",
            isAdmin = false
        )

        // When & Then
        assertThrows<PlanMateException.AuthorizationException.AdminPrivilegesRequiredException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }


    @Test
    fun `should throw InvalidStateNameException when new state name is blank`() = runTest {
        // Given
        val projectId = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = " "
        every { validationUseCase.isValidName(newState) } returns false
        coEvery {
            repository.updateProjectStateById(
                UUID.fromString(projectId),
                oldState,
                newState
            )
        } returns false
        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when old state name is blank`() = runTest {
        // Given
        val projectId = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = " "
        val newState = "Done"
        every { validationUseCase.isValidName(oldState) } returns false
        coEvery {
            repository.updateProjectStateById(
                UUID.fromString(projectId),
                oldState,
                newState
            )
        } returns false

        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @Test
    fun `should throw SameStateNameException when old state is equal to new state`() = runTest {
        // Given
        val projectId = "123e4567-e89b-12d3-a456-426614174000"
        val oldState = "Done"
        val newState = "Done"
        every { validationUseCase.areIdentical(oldState, newState) } returns true

        coEvery {
            repository.updateProjectStateById(
                UUID.fromString(projectId),
                oldState,
                newState
            )
        } returns false

        // When & Then
        assertThrows<SameStateNameException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            " ",
            "old",
            "db373589-b656-4e68 a7c0-2ccc705ca169",
            " db373589-b656#4e68@a7c0-2ccc705ca169"
        ]
    )
    fun `should throw InvalidProjectID when project ID is not a valid UUID`(invalidID: String) =
        runTest {
            // Given
            val oldState = "Done"
            val newState = "Completed"
            every { validationUseCase.isValidUUID(invalidID) } returns false

            // When & Then
            assertThrows<InvalidProjectIDException> {
                useCase.updateState(invalidID, oldState, newState)
            }

        }


}