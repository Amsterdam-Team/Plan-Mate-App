package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.PROJECT_1
import io.mockk.every
import io.mockk.mockk
import logic.repository.LogRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GetProjectLogsUseCaseTest {

    private lateinit var logRepository: LogRepository
    private lateinit var viewProjectHistoryUseCase: ViewProjectHistoryUseCase

    @BeforeEach
    fun setup() {
        logRepository = mockk()
        viewProjectHistoryUseCase = ViewProjectHistoryUseCase(logRepository)
    }

    @Test
    fun `should return the correct logs list when projectId is valid`() {
        // Given
        val projectId = PROJECT_1.id
        val logs = LOGS_FOR_PROJECT_1
        every { logRepository.viewLogsById(projectId) } returns logs

        // When
        val result = viewProjectHistoryUseCase.execute(projectId.toString())

        // Then
        assertThat(result).isEqualTo(logs)
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`(){
        // When & Then
        assertThrows<InvalidProjectIDException> {
            viewProjectHistoryUseCase.execute("not-uuid")
        }
    }

    @Test
    fun `should throw InvalidProjectIDException when input is null`(){
        // When & Then
        assertThrows<InvalidProjectIDException> {
            viewProjectHistoryUseCase.execute(null)
        }
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val invalidProjectId = UUID.randomUUID()
        every {
            logRepository.viewLogsById(invalidProjectId)
        } throws ProjectNotFoundException

        // When & Then
        assertThrows<ProjectNotFoundException> {
            viewProjectHistoryUseCase.execute(invalidProjectId.toString())
        }
    }
}
