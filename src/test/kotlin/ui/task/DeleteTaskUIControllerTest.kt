package ui.task

import console.ConsoleIO
import helpers.DeleteTaskTestFactory.TASK_1
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecases.task.DeleteTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteTaskUIControllerTest {

    private lateinit var useCase: DeleteTaskUseCase
    private lateinit var controller: DeleteTaskUIController
    private lateinit var consoleIO: ConsoleIO

    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        controller = DeleteTaskUIController(useCase,consoleIO)
    }

    @Test
    fun `should print success message when task deleted`() {
        // Given
        every { consoleIO.readFromUser() } returns TASK_1.id.toString()
        every { useCase.execute(TASK_1.id.toString()) } returns true
        // When
        controller.execute()

        // Then
        verify(exactly = 1) { consoleIO.println("✅ Task deleted successfully.") }
    }

    @Test
    fun `should print fail message when task is not deleted`() {
        // Given
        every { consoleIO.readFromUser() } returns TASK_1.id.toString()
        every { useCase.execute(TASK_1.id.toString()) } returns false
        // When
        controller.execute()

        // Then
        verify(exactly = 1) { consoleIO.println("❌ Failed to delete task. Please try again.") }
    }

    @Test
    fun `should call deleteTaskUseCase with params when task is selected`() {
        // Given
        every { consoleIO.readFromUser() } returns TASK_1.id.toString()

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.execute(TASK_1.id.toString()) }
    }

}