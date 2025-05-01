package ui.controllers

import com.google.common.truth.Truth.assertThat
import helpers.DeleteTaskTestFactory.ALL_PROJECTS
import helpers.DeleteTaskTestFactory.PROJECT_1
import helpers.DeleteTaskTestFactory.TASK_1
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.ProjectRepository
import logic.usecases.DeleteTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class DeleteTaskUIControllerTest {

    private lateinit var deleteTaskUseCase: DeleteTaskUseCase
    private lateinit var projectRepository: ProjectRepository
    private lateinit var controller: DeleteTaskUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        deleteTaskUseCase = mockk(relaxed = true)
        projectRepository = mockk()
        controller = DeleteTaskUIController(deleteTaskUseCase, projectRepository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }


    @Test
    fun `should print all project IDs`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS

        // When
        controller.start()

        // Then
        assertThat(outContent.toString()).contains(PROJECT_1.id.toString())
    }

    @Test
    fun `should call deleteTaskUseCase with params when task is selected`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        provideInput(TASK_1.id.toString())

        // When
        controller.start()

        // Then
        verify (exactly = 1){ deleteTaskUseCase.execute(TASK_1.id.toString()) }
    }


    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
