package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class DeleteStateUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: DeleteStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = DeleteStateUseCase(repository)
    }

    @Test
    fun `deleteStateById should  delete state by state id and project id  from repository when called`() {
        //When
        val stateId = UUID.fromString("db373589-b656-4e68-a7c0-2ccc705ca169")
        val projectID = UUID.fromString("db373589-b656-4e68-a7c0-2ccc705ca169")

        useCase(stateId = stateId, projectId = projectID)
        // Then
        verify(exactly = 1) { repository.deleteStateById(stateId = stateId, projectId = projectID) }
    }


    @Test
    fun `delete use case should return true when state is successfully deleted`() {
        // Given
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val stateId = UUID.fromString("00000000-0000-0000-0000-000000000002")

        every { repository.deleteStateById(stateId = stateId, projectId = projectId) } returns true
        // When
        val result = useCase(stateId = stateId, projectId = projectId)

        assertThat(result).isTrue()
        verify(exactly = 1) { repository.deleteStateById(stateId = stateId, projectId = projectId) }
    }

    @Test
    fun `delete use case should return false when state is failed deleted`() {
        // Given
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val stateId = UUID.fromString("00000000-0000-0000-0000-000000000002")

        every { repository.deleteStateById(projectId, stateId) } returns false

        // When
        val result = useCase(stateId = stateId, projectId = projectId)
        // Then

        assertThat(result).isFalse()
        verify(exactly = 1) { repository.deleteStateById(stateId = stateId, projectId = projectId) }
    }


    @Test
    fun `should throw StateNotFoundException when project does not exist`() {
        // Given
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val stateId = UUID.fromString("00000000-0000-0000-0000-000000000002")

        every {
            repository.deleteStateById(stateId = stateId, projectId = projectId)
        } throws PlanMateException.NotFoundException.StateNotFoundException

        // When/Then
        assertThrows<PlanMateException.NotFoundException.StateNotFoundException> {
            useCase(stateId = stateId, projectId = projectId)
        }

    }


}
