package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.PROJECT_1
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.LogRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import org.junit.jupiter.api.assertThrows

class ViewProjectHistoryUseCaseTest {

    private lateinit var logRepository: LogRepository
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
        assertThat(result).isEqualTo(logs)
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`()=runTest{
        // When & Then
        assertThrows<InvalidProjectIDException> {
            getProjectHistoryUseCase.execute("not-uuid")
        }
    }

    @Test
    fun `should throw InvalidProjectIDException when input is null`()=runTest{
        // When & Then
        assertThrows<InvalidProjectIDException> {
            getProjectHistoryUseCase.execute(null)
        }
    }


}
