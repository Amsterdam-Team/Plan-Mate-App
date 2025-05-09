package ui.logs

import com.google.common.truth.Truth
import helper.INVALID_ID_FORMAT
import helper.TASK_NOT_FOUND
import helper.invalidId
import helper.taskLogs
import helper.validId
import io.mockk.CapturingSlot
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.usecases.logs.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO

class ViewTaskLogsUIControllerTest {
    private lateinit var useCase : ViewTaskLogsUseCase
    private lateinit var uiController : ViewTaskLogsUIController
    private lateinit var consoleIO: ConsoleIO


    @BeforeEach
    fun setup(){
        useCase = mockk()
        consoleIO = mockk()
        uiController = ViewTaskLogsUIController(useCase,consoleIO)
    }

    @Test
    fun `should print logs of task when logs are found`() = runTest {
        val slot = CapturingSlot<String>()

        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returns validId.toString()
        coEvery { useCase.viewTaskLogs(validId.toString()) } returns taskLogs()

        uiController.execute()

        Truth.assertThat(slot.equals((taskLogs()[0].message) + (taskLogs()[1].message)))
    }

    @Test
    fun `should print Invalid ID Format message when use case throw InvalidTaskIDException`() = runTest {
        val slot = CapturingSlot<String>()

        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returns invalidId.toString()
        coEvery { useCase.viewTaskLogs(invalidId) } throws PlanMateException.ValidationException.InvalidTaskIDException

        uiController.execute()

        Truth.assertThat(slot.equals(INVALID_ID_FORMAT))
    }

    @Test
    fun `should print Task Not Found message when use case throw TaskNotFoundException`() = runTest {
        val slot = CapturingSlot<String>()

        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returns invalidId.toString()
        coEvery { useCase.viewTaskLogs(invalidId.toString()) } throws PlanMateException.NotFoundException.TaskNotFoundException

        uiController.execute()

        Truth.assertThat(slot.equals(TASK_NOT_FOUND))
    }
}