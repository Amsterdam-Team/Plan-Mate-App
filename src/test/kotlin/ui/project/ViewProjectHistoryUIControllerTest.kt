package ui.project


import console.ConsoleIO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.usecases.project.GetProjectHistoryUseCase
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.PROJECT_1
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.formatLogItem

class ViewProjectHistoryUIControllerTest {

    private lateinit var useCase: GetProjectHistoryUseCase
    private lateinit var controller: ViewProjectHistoryUIController
    private lateinit var consoleIO: ConsoleIO
    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        controller = ViewProjectHistoryUIController(
            getProjectHistoryUseCase = useCase,
            consoleIO = consoleIO)
    }

    @Test
    fun `should call read from user function`() = runTest{
        //When
        controller.execute()
        //Then
        verify (exactly = 1){ consoleIO.readFromUser() }
    }

    @Test
    fun `should call viewProjectHistoryUseCase when user enter the input`() = runTest{
        // Given
        val selectedProjectId = PROJECT_1.id.toString()
        coEvery  { consoleIO.readFromUser() } returns selectedProjectId

        // When
        controller.execute()

        // Then
        coVerify (exactly = 1) { useCase.execute(selectedProjectId) }
    }

    @Test
    fun `should print logs for selected project`() = runTest{
        // Given
        val selectedProjectId = PROJECT_1.id.toString()
        every { consoleIO.readFromUser() } returns selectedProjectId
        coEvery { useCase.execute(selectedProjectId)} returns LOGS_FOR_PROJECT_1
        // When
        controller.execute()

        // Then
        LOGS_FOR_PROJECT_1.forEach {
            verify(exactly = 1) { consoleIO.println(formatLogItem(it)) }
        }
    }
}
