package ui

import com.google.common.base.CharMatcher.any
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
    fun `should call the editTaskName correctly when take input from user`() {

        every { consoleIO.readFromUser() } returnsMany listOf("1","task-id", "new-name")
        every { useCase.editTaskName(any(), any()) } returns true

        uiController.execute()

        verify { useCase.editTaskName("task-id", "new-name") }
    }

    @Test
    fun `should call the editTaskNameAndSTATE correctly when take input from user`() {

        every { consoleIO.readFromUser() } returnsMany listOf("2","task-id", "new-name","new-state")
        every { useCase.editTaskName(any(), any()) } returns true

        uiController.execute()

        verify { useCase.editTaskNameAndState("task-id", "new-name","new-state") }
    }
    @Test
    fun `should print success to user when complete executing`() {
        every { consoleIO.readFromUser() } returnsMany listOf("1","task-id", "new-name")
        every { useCase.editTaskName(any(), any()) } returns true

        uiController.execute()
        verify { consoleIO.println("Task name updated successfully") }
    }
}