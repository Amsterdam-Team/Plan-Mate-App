package ui.project

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.usecases.project.DeleteProjectUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIOImpl
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
    fun `should call delete project usecase when try to delete project`() = runTest {
        // giver
        val projectId = UUID.randomUUID()
        every { uiController.consoleIO.readFromUser() } returns projectId.toString()

        // when
        uiController.execute()

        // then
        coVerify(exactly = 1) { useCase.deleteProject(projectId.toString()) }
    }

    @Test
    fun `should print success message to user when deleting project complete successfully`()= runTest {
        // giver
        val projectId = UUID.randomUUID()
        every { uiController.consoleIO.readFromUser() } returns projectId.toString()
        coEvery { useCase.deleteProject(projectId.toString()) } returns true
        // when
        uiController.execute()

        // then
        verify { consoleIO.println("Project deleted successfully") }
    }

    @Test
    fun `should print failure message to user when deleting project does not complete successfully`()= runTest {
        // given
        val projectId = UUID.randomUUID()
        every { uiController.consoleIO.readFromUser() } returns projectId.toString()
        coEvery { useCase.deleteProject(projectId.toString()) } returns false
        // when
        uiController.execute()
        // then
        verify { consoleIO.println("Failed deleting project") }
    }
}