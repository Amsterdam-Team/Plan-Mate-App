package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    fun `deleteStateById should  delete state by state id and project id  from repository when called`() = runTest{
        //When
        val oldState = "oldState"

        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")

        useCase(projectId = projectId, oldState = oldState)
        // Then
        coVerify(exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = oldState) }
    }
//

    @Test
    fun `delete use case should return true when state is successfully deleted`() = runTest{
        // Given
        val oldState = "oldState"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")

        coEvery { repository.deleteStateById(projectId = projectId, oldState = oldState) } returns true
        // When
        val result = useCase(projectId = projectId, oldState = oldState)

        assertThat(result).isTrue()
        coVerify (exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = oldState) }
    }

    @Test
    fun `delete use case should return false when state is failed deleted`() = runTest{
        // Given
        val oldState = "oldState"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        coEvery { repository.deleteStateById(projectId, oldState) } returns false

        // When
        val result = useCase(projectId = projectId, oldState = oldState)
        // Then

        assertThat(result).isFalse()
        coVerify(exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = oldState) }
    }


    @Test
    fun `should throw StateNotFoundException when project does not exist`() = runTest{
        // Given
        val oldState = "oldState"
        val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")

        coEvery {
            repository.deleteStateById(projectId = projectId, oldState = oldState)
        } throws PlanMateException.NotFoundException.StateNotFoundException

        // When/Then
        assertThrows<PlanMateException.NotFoundException.StateNotFoundException> {
            useCase(projectId = projectId, oldState = oldState)
        }

    }


}
