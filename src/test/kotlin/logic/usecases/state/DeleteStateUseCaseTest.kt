package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.dummyProject
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
        val oldState = "oldState"

        val projectId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.getProject(any()) } returns dummyProject

        useCase(projectId = projectId, oldState = oldState)
        // Then
        verify(exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = oldState) }
    }

    //
    @Test
    fun `should call repository to delete state when valid projectId and state are provided`() {
        // Given
        val projectId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        val oldState = "To-Do"
        every { repository.getProject(any()) } returns dummyProject

        every { repository.deleteStateById(projectId, oldState) } returns true

        // When
        useCase(projectId = projectId, oldState = oldState)

        // Then
        verify(exactly = 1) {
            repository.deleteStateById(projectId, oldState)
        }
    }


    @Test
    fun `delete use case should return true when state is successfully deleted`() {
        // Given
        val oldState = "oldState"
        //   val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val projectId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.getProject(any()) } returns dummyProject

        every { repository.deleteStateById(projectId = projectId, oldState = oldState) } returns true
        // When
        val result = useCase(projectId = projectId, oldState = oldState)

        assertThat(result).isTrue()
        verify(exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = oldState) }
    }

    @Test
    fun `delete use case should return false when state is failed deleted`() {
        // Given
        val oldState = "oldState"
        //  val projectId = UUID.fromString("00000000-0000-0000-0000-000000000001")
        val projectId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.getProject(any()) } returns dummyProject

        every { repository.deleteStateById(projectId, oldState) } returns false

        // When
        val result = useCase(projectId = projectId, oldState = oldState)
        // Then

        assertThat(result).isFalse()
        verify(exactly = 1) { repository.deleteStateById(projectId = projectId, oldState = oldState) }
    }


    @Test
    fun `should throw StateNotFoundException when project does not exist`() {
        // Given
        val oldState = "oldState"
        val projectId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.getProject(any()) } returns dummyProject

        every {
            repository.deleteStateById(projectId = projectId, oldState = oldState)
        } throws PlanMateException.NotFoundException.StateNotFoundException

        // When/Then
        assertThrows<PlanMateException.NotFoundException.StateNotFoundException> {
            useCase(projectId = projectId, oldState = oldState)
        }

    }

    @Test
    fun `should throw InvalidStateNameException when old state name is blank`() {
        // Given
        val projectId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        val oldState = " "
        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase(projectId = projectId, oldState = oldState)
        }
    }


    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val projectId = UUID.randomUUID()
        val oldState = "To-Do"

        every { repository.getProject(projectId) } throws ProjectNotFoundException

        // When & Then
        assertThrows<ProjectNotFoundException> {
            useCase(projectId = projectId, oldState = oldState)
        }

        verify(exactly = 0) { repository.deleteStateById(any(), any()) }
    }


}
