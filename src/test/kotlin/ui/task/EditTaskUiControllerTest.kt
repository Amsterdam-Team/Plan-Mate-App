package ui.task

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecases.task.EditTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIOImpl

class EditTaskUiControllerTest {
    lateinit var useCase: EditTaskUseCase
    lateinit var uiController: EditTaskUiController
    lateinit var consoleIO: ConsoleIOImpl

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        uiController = EditTaskUiController(useCase, consoleIO)
    }


    @Test
    fun `should print success to user when complete executing editing task`() {
        every { consoleIO.readFromUser() } returnsMany listOf("1","task-id", "new-name")
        every { useCase.editTask("1","task-id", "new-name")} returns true
        uiController.execute()
        verify { consoleIO.println("Task updated successfully") }
    }
    @Test
    fun `should print failure message to user when failed with  executing editing task`() {
        every { consoleIO.readFromUser() } returnsMany listOf("1","task-id", "new-name")
        every { useCase.editTask("1","task-id", "new-name")} returns false

        uiController.execute()
        verify { consoleIO.println("Failed updating task") }
    }
}