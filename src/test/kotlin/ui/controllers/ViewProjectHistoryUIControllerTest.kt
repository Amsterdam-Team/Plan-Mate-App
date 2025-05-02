package ui.controllers


import com.google.common.truth.Truth.assertThat
import helpers.ViewProjectHistoryTestFactory.ALL_PROJECTS
import helpers.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import helpers.ViewProjectHistoryTestFactory.PROJECT_1
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.repository.ProjectRepository
import logic.repository.LogRepository
import logic.usecases.ViewProjectHistoryUseCase
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ViewProjectHistoryUIControllerTest {

    private lateinit var useCase: ViewProjectHistoryUseCase
    private lateinit var repository: ProjectRepository
    private lateinit var logRepository: LogRepository
    private lateinit var controller: ViewProjectHistoryUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        repository = mockk()
        controller = ViewProjectHistoryUIController(useCase, repository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should print all project IDs`() {
        // Given
        every { repository.getProjects() } returns ALL_PROJECTS

        // When
        controller.start()

        // Then
        assertThat(outContent.toString()).contains(PROJECT_1.id.toString())
    }

    @Test
    fun `should call viewProjectHistoryUseCase with params when project is selected`() {
        // Given
        val selectedProjectId = PROJECT_1.id
        every { repository.getProjects() } returns ALL_PROJECTS
        every { logRepository.viewLogsById(selectedProjectId) } returns LOGS_FOR_PROJECT_1

        provideInput(selectedProjectId.toString())

        // When
        controller.start()

        // Then
        verify (exactly = 1) { useCase.execute(selectedProjectId.toString()) }
    }

    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
