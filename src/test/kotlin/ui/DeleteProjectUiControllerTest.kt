package ui

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.task.EditTaskUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl
import java.io.ByteArrayInputStream
import java.util.UUID

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
    fun `should call delete project usecase when try to delete project`() {
        val projectId = UUID.randomUUID()
        every { uiController.consoleIO.readFromUser()} returns projectId.toString()
        uiController.execute()


        verify(exactly = 1) { useCase.deleteProject(projectId.toString()) }
    }

    @Test
    fun `should print success message to user when deleting project complete successfully`() {
        val projectId = UUID.randomUUID()
        every { uiController.consoleIO.readFromUser()} returns projectId.toString()
        every { useCase.deleteProject(projectId.toString())} returns true
        uiController.execute()
        verify { consoleIO.println("Project deleted successfully") }
    }
}