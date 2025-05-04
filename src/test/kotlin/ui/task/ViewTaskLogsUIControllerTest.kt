package ui.task

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.ViewTaskLogsUIController
import logic.usecases.testFactory.INVALID_ID_FORMAT
import logic.usecases.testFactory.TASK_NOT_FOUND
import logic.usecases.testFactory.invalidId
import logic.usecases.testFactory.simulateConsoleInteraction
import logic.usecases.testFactory.taskLogs
import logic.usecases.testFactory.validId

class ViewTaskLogsUIControllerTest {
    private lateinit var useCase : ViewTaskLogsUseCase
    private lateinit var uiController : ViewTaskLogsUIController

    @BeforeEach
    fun setup(){
        useCase = mockk()
        uiController = ViewTaskLogsUIController(useCase)
    }

    @Test
    fun `should print logs of task when logs are found`(){

        every { useCase.viewTaskLogs(validId.toString()) } returns taskLogs()

        val output = simulateConsoleInteraction(validId.toString()) {
            uiController.execute()
        }

        assert(output.contains(taskLogs()[0].message))
        assert(output.contains(taskLogs()[1].message))

    }

    @Test
    fun `should print Invalid ID Format message when use case throw InvalidTaskIDException`(){

        every { useCase.viewTaskLogs(invalidId) } throws PlanMateException.ValidationException.InvalidTaskIDException

        val output = simulateConsoleInteraction(invalidId.toString()) {
            uiController.execute()
        }

        assert(output.contains(INVALID_ID_FORMAT))
    }

    @Test
    fun `should print Task Not Found message when use case throw TaskNotFoundException`(){

        every { useCase.viewTaskLogs(invalidId.toString()) } throws PlanMateException.NotFoundException.TaskNotFoundException

        val output = simulateConsoleInteraction(invalidId.toString()) {
            uiController.execute()
        }
        assert(output.contains(TASK_NOT_FOUND))
    }
}