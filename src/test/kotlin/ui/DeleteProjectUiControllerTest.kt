package ui

import io.mockk.mockk
import io.mockk.verify
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.task.EditTaskUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl

class DeleteProjectUiControllerTest {
    lateinit var useCase: DeleteProjectUseCase
    lateinit var uiController: DeleteProjectUiController
    lateinit var consoleIO: ConsoleIOImpl

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        uiController = DeleteProjectUiController(useCase, consoleIO)
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