package ui.project


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.ProjectRepository
import logic.usecases.project.ViewProjectHistoryUseCase
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.ALL_PROJECTS
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.LOGS_FOR_PROJECT_1
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.PROJECT_1
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ViewProjectHistoryUIControllerTest {

    private lateinit var useCase: ViewProjectHistoryUseCase
    private lateinit var repository: ProjectRepository
    private lateinit var controller: ViewProjectHistoryUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = mockk(relaxed = true)
        controller = ViewProjectHistoryUIController(useCase, repository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should print all project IDs`() {
        // Given
        every { repository.getProjects() } returns ALL_PROJECTS

        // When
        controller.execute()

        // Then
        ALL_PROJECTS.forEach {
            assertThat(outContent.toString()).contains(it.id.toString())
        }
    }

    @Test
    fun `should call viewProjectHistoryUseCase with params when project is selected`() {
        // Given
        val selectedProjectId = PROJECT_1.id
        every { repository.getProjects() } returns ALL_PROJECTS
        provideInput(selectedProjectId.toString())

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.execute(selectedProjectId.toString()) }
    }

    @Test
    fun `should print logs for selected project`() {
        // Given
        val selectedProjectId = PROJECT_1.id
        every { repository.getProjects() } returns ALL_PROJECTS
        provideInput(selectedProjectId.toString())
        every { useCase.execute(selectedProjectId.toString())} returns LOGS_FOR_PROJECT_1
        // When
        controller.execute()

        // Then
        LOGS_FOR_PROJECT_1.forEach {
            assertThat(outContent.toString()).contains(it.message)
        }
    }



    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
