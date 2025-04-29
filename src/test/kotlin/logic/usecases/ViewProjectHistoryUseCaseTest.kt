package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.entities.LogItem
import logic.repository.LogRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
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
        val projectId = UUID.randomUUID()
        val now = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.UTC)
        val logs = listOf(
            LogItem(UUID.randomUUID(), "Log 1", now, projectId),
            LogItem(UUID.randomUUID(), "Log 2", now, projectId)
        )
        every { logRepository.viewLogsById(projectId) } returns logs

        // When
        val result = viewProjectHistoryUseCase.execute(projectId)

        // Then
        assertThat(result).isEqualTo(logs)
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        val projectId = UUID.randomUUID()
        every { logRepository.viewLogsById(projectId) } throws PlanMateException.NotFoundException.ProjectNotFoundException

        // When & Then
        assertThrows<PlanMateException.NotFoundException.ProjectNotFoundException> {
            viewProjectHistoryUseCase.execute(projectId)
        }
    }


}
