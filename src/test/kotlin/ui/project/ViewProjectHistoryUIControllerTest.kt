package ui.project

import com.google.common.truth.Truth
import logic.usecases.project.helper.ViewProjectHistoryTestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.usecases.project.ViewProjectHistoryUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
        val projectId = ViewProjectHistoryTestFactory.PROJECT_1.id.toString()
        every { projectRepository.getProjects() } returns ViewProjectHistoryTestFactory.ALL_PROJECTS
        provideInput(projectId)
        every { viewProjectHistoryUseCase.execute(projectId) } returns emptyList()

        // When
        controller.execute()

        // Then
        Truth.assertThat(outContent.toString()).contains(projectId.toString())
        verify { projectRepository.getProjects() }
    }

    @Test
    fun `should call viewProjectHistoryUseCase with params when project is selected`() {
        // Given
        val selectedProjectId = ViewProjectHistoryTestFactory.PROJECT_1.id
        every { projectRepository.getProjects() } returns ViewProjectHistoryTestFactory.ALL_PROJECTS
        every { logRepository.viewLogsById(selectedProjectId) } returns ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
        provideInput(selectedProjectId.toString())

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { viewProjectHistoryUseCase.execute(selectedProjectId.toString()) }
    }

    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}