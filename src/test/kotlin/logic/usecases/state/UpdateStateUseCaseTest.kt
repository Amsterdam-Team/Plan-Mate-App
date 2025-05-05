package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import data.datasources.DataSource
import data.repository.project.ProjectRepositoryImpl
import io.mockk.*
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.usecases.project.helper.createProject
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
        val projectID ="db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "In Progress"
        val newState = "In Review"

        // When
        val result = useCase.updateState(projectID,oldState,newState)

        // Then
        assertThat(result).isTrue()

    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val projectId = "db373589-b656-4e68-a7c1-2ccc705ca169"
        val oldState = "Done"
        val newState = "Finished"


        // When & Then
        assertThrows<ProjectNotFoundException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }
    @Test
    fun `should throw EmptyDataException when projects is empty`() {
        // Given
        val projectId = "db373589-b656-4e68-a7c1-2ccc705ca169"
        val oldState = "Done"
        val newState = "Finished"

        // When & Then
        assertThrows<EmptyDataException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when new state name is blank`() {
        // Given
        val projectId = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = " "
        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }
    @Test
    fun `should throw InvalidStateNameException when old state name is blank`() {
        // Given
        val projectId = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = " "
        val newState = "Done"
        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.updateState(projectId, oldState, newState)
        }
    }

    @Test
    fun `should throw SameStateNameException when old state is equal to new state`() {
        // Given
        val projectId = "db373589-b656-4e68-a7c0-2ccc705ca169"
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
        val oldState = "Done"
        val newState = "Completed"
        // When & Then
        assertThrows<InvalidProjectIDException> {
            useCase.updateState(invalidID, oldState, newState)
        }

    }


}