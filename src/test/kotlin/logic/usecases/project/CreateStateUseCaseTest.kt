package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.ResultStatus
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
    fun `should add state when state is valid`() {
        // Given
        val project = createProject()
        val newState = "In Progress"

        // When
        val result = useCase.execute(project.id, newState)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Success::class.java)
    }

    @Test
    fun `should throw InvalidStateNameException when state name is blank`() {
        // Given
        val project = createProject()
        val blankState = "  "

        // When & Then
        assertThrows<InvalidStateNameException> {
            useCase.execute(project.id, blankState)
        }
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val projectId = UUID.randomUUID()
        val state = "Done"

        // When & Then
        assertThrows<ProjectNotFoundException> {
            useCase.execute(projectId, state)
        }
    }
}