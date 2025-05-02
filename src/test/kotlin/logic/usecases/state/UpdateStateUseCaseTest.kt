package logic.usecases.state

import io.mockk.*
import logic.repository.ProjectRepository
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID
import kotlin.test.Test

class UpdateStateUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: UpdateStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = UpdateStateUseCase(repository)
    }

    @Test
    fun `should update state name when inputs are valid`() {
        // Given
        val projectID = UUID.fromString("db373589-b656-4e68-a7c0-2ccc705ca169")
        val oldState = "In Progress"
        val newState = "In Review"

        // When & Then
        verify(exactly = 1) { useCase.updateState(projectID, newState, oldState) }

    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val projectId = UUID.randomUUID()
        val oldState = "Done"
        val newState = "Finished"

        // When & Then
        assertThrows<ProjectNotFoundException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when new state name or old state name is blank`() {
        // Given
        val projectId = UUID.randomUUID()
        val oldState = "Done"
        val newState = " "
        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @Test
    fun `should throw SameStateNameException when old state is equal to new state`() {
        // Given
        val projectId = UUID.randomUUID()
        val oldState = "Done"
        val newState = "Done"
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
    fun `should throw InvalidProjectID when project ID is not a valid UUID`(invalidID: String) {
        // Given
        val projectId = UUID.fromString(invalidID)
        val oldState = "Done"
        val newState = "Completed"
        // When & Then
        assertThrows<InvalidProjectIDException> {
            useCase.updateState(projectId, oldState, newState)
        }

    }


}