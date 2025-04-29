package ui.controllers


import com.google.common.truth.Truth.assertThat
import helpers.ViewProjectHistoryTestFactory.ALL_PROJECTS
import helpers.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import helpers.ViewProjectHistoryTestFactory.LOG_1
import helpers.ViewProjectHistoryTestFactory.PROJECT_1
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import logic.exception.PlanMateException
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
        viewProjectHistoryUseCase = mockk(relaxed = true)
        projectRepository = mockk()
        logRepository = mockk()
        controller = ViewProjectHistoryUIController(viewProjectHistoryUseCase, projectRepository, logRepository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should throw NoProjectsFoundException when there are no projects`() {
        // Given
        every { projectRepository.getProjects() } returns emptyList()

        // When & Then
        assertThrows<PlanMateException.NotFoundException.ProjectNotFoundException> {
            controller.viewProjectHistoryUI()
        }
    }

    @Test
    fun `should print all project IDs when projects are available`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS

        // When
        controller.viewProjectHistoryUI()

        // Then
        assertThat(outContent.toString()).contains(PROJECT_1.id.toString())
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS

        // When & Then
        provideInput("not-a-uuid")

        assertThrows<PlanMateException.ValidationException.InvalidProjectIDException> {
            controller.viewProjectHistoryUI()
        }
    }



    @Test
    fun `should print log messages when logs exist for the selected project`() {
        // Given
        val selectedProjectId = PROJECT_1.id
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { logRepository.viewLogsById(selectedProjectId) } returns LOGS_FOR_PROJECT_1

        // When
        provideInput(selectedProjectId.toString())
        controller.viewProjectHistoryUI()

        // Then
        assertThat(outContent.toString()).contains(LOG_1.message)
    }

    @Test
    fun `should call viewProjectHistoryUseCase with correct params when project is selected`() {
        // Given
        val selectedProjectId = PROJECT_1.id
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { logRepository.viewLogsById(selectedProjectId) } returns LOGS_FOR_PROJECT_1

        provideInput(selectedProjectId.toString())

        // When
        controller.viewProjectHistoryUI()

        // Then
        verify (exactly = 1) { viewProjectHistoryUseCase.execute(selectedProjectId) }
    }

    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
