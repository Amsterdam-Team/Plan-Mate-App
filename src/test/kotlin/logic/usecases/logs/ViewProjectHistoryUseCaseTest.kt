package logic.usecases.logs

import com.google.common.truth.Truth
import helper.LogFactory.LOGS_FOR_PROJECT_1
import helper.ProjectFactory.PROJECT_1
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ILogRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ViewProjectHistoryUseCaseTest {

    private lateinit var logRepository: ILogRepository
    private lateinit var getProjectHistoryUseCase: GetProjectHistoryUseCase

    @BeforeEach
    fun setup() {
        logRepository = mockk()
        getProjectHistoryUseCase = GetProjectHistoryUseCase(logRepository)
    }

    @Test
    fun `should return the correct logs list when projectId is valid`() = runTest {
        // Given
        val projectId = PROJECT_1.id
        val logs = LOGS_FOR_PROJECT_1
        coEvery { logRepository.viewLogsById(projectId) } returns logs

        // When
        val result = getProjectHistoryUseCase.execute(projectId.toString())

        // Then
        Truth.assertThat(result).isEqualTo(logs)
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`()= runTest {
        // When & Then
        assertThrows<InvalidProjectIDException> {
            getProjectHistoryUseCase.execute("not-uuid")
        }
    }

    @Test
    fun `should throw InvalidProjectIDException when input is null`()= runTest {
        // When & Then
        assertThrows<InvalidProjectIDException> {
            getProjectHistoryUseCase.execute(null)
        }
    }


}