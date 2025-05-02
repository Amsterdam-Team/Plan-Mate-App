package uiController

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.TaskIdNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.ViewTaskLogsUIController
import utils.hepler.TASK_ID_NOT_FOUND
import utils.hepler.INVALID_ID_FORMAT
import utils.hepler.invalidId
import utils.hepler.simulateConsoleInteraction
import utils.hepler.taskLogs
import utils.hepler.validId

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

        assert(output.contains(taskLogs().toString()))
    }

    @Test
    fun `should print Invalid ID Format message when use case throw InvalidTaskIDException`(){

        every { useCase.viewTaskLogs(invalidId) } throws InvalidTaskIDException
        val output = simulateConsoleInteraction(invalidId.toString()) {
            uiController.execute()
        }

        assert(output.contains(INVALID_ID_FORMAT))
    }

    @Test
    fun `should print Task ID Not Found message when use case throw TaskIdNotFoundException`(){

        every { useCase.viewTaskLogs(invalidId.toString()) } throws TaskIdNotFoundException

        val output = simulateConsoleInteraction(invalidId.toString()) {
            uiController.execute()
        }
        assert(output.contains(TASK_ID_NOT_FOUND))
    }
}