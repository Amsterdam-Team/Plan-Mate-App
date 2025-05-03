package ui.task

import com.google.common.truth.Truth
import helpers.DeleteTaskTestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.ProjectRepository
import logic.usecases.task.DeleteTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class DeleteTaskUIControllerTest {

    private lateinit var useCase: DeleteTaskUseCase
    private lateinit var repository: ProjectRepository
    private lateinit var controller: DeleteTaskUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        repository = mockk()
        controller = DeleteTaskUIController(useCase, repository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }


    @Test
    fun `should print all project IDs`() {
        // Given
        every { repository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS

        // When
        controller.execute()

        // Then
        Truth.assertThat(outContent.toString()).contains(DeleteTaskTestFactory.PROJECT_1.id.toString())
    }

    @Test
    fun `should call deleteTaskUseCase with params when task is selected`() {
        // Given
        every { repository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS
        provideInput(DeleteTaskTestFactory.TASK_1.id.toString())

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.execute(DeleteTaskTestFactory.TASK_1.id.toString()) }
    }


    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}