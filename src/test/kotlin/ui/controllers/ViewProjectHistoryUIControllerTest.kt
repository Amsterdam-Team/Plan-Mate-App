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

    private lateinit var viewProjectHistoryUseCase: ViewProjectHistoryUseCase
    private lateinit var projectRepository: ProjectRepository
    private lateinit var logRepository: LogRepository
    private lateinit var controller: ViewProjectHistoryUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        projectRepository = mockk(relaxed = true)
        logRepository = mockk()
        viewProjectHistoryUseCase = ViewProjectHistoryUseCase(logRepository)
        controller = ViewProjectHistoryUIController(viewProjectHistoryUseCase, projectRepository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should print all project IDs`() {
        // Given
        val projectId = PROJECT_1.id.toString()
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        provideInput(projectId)
        every { viewProjectHistoryUseCase.execute(projectId) } returns emptyList()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains(projectId.toString())
        verify { projectRepository.getProjects() }
    }

    @Test
    fun `should call viewProjectHistoryUseCase with params when project is selected`() {
        // Given
        val selectedProjectId = PROJECT_1.id
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { logRepository.viewLogsById(selectedProjectId) } returns LOGS_FOR_PROJECT_1
        provideInput(selectedProjectId.toString())

        // When
        controller.execute()

        // Then
        verify (exactly = 1) { viewProjectHistoryUseCase.execute(selectedProjectId.toString()) }
    }

    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
