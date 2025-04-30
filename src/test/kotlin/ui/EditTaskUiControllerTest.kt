package ui

import io.mockk.mockk
import io.mockk.verify
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.task.EditTaskUseCase
import org.junit.jupiter.api.Assertions.*
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
    fun `should take input from user when execute`() {
        uiController.execute()
        verify { consoleIO.readFromUser() }
    }

    @Test
    fun `should print result to user when complete executing`() {
        uiController.execute()
        verify { consoleIO.println(any()) }
    }
}