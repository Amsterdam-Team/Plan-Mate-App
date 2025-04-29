package logic.usecases

import com.google.common.truth.Truth.assertThat
import helpers.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import helpers.ViewProjectHistoryTestFactory.PROJECT_1
import io.mockk.every
import io.mockk.mockk
import logic.repository.LogRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exception.PlanMateException
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
        val result = viewProjectHistoryUseCase.execute(projectId)

        // Then
        assertThat(result).isEqualTo(logs)
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val invalidProjectId = UUID.randomUUID()
        every {
            logRepository.viewLogsById(invalidProjectId)
        } throws PlanMateException.NotFoundException.ProjectNotFoundException

        // When & Then
        assertThrows<PlanMateException.NotFoundException.ProjectNotFoundException> {
            viewProjectHistoryUseCase.execute(invalidProjectId)
        }
    }
}
