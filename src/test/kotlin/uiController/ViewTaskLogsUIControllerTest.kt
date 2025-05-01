package uiController

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.TaskIDNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.ViewTaskLogsUIController
import utils.hepler.TASK_ID_NOT_FOUND
import utils.hepler.INVALID_ID_FORMAT
import utils.hepler.invalidId
import utils.hepler.readInputFromConsole
import utils.hepler.taskLogs
import utils.hepler.validId
import java.util.UUID

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

        val output = readInputFromConsole(validId.toString())

        every { useCase.viewTaskLogs(validId) } returns taskLogs()
        uiController. viewTaskLogsUIController()

        assert(output.contains(taskLogs().toString()))
    }

    @Test
    fun `should print Invalid ID Format message when use case throw InvalidTaskIDException`(){
        val output = readInputFromConsole(invalidId)

        every { useCase.viewTaskLogs(UUID.fromString(invalidId)) } throws InvalidTaskIDException
        uiController. viewTaskLogsUIController()

        assert(output.contains(INVALID_ID_FORMAT))
    }

    @Test
    fun `should print Task ID Not Found message when use case throw TaskIdNotFoundException`(){

        val output = readInputFromConsole(validId.toString())

        every { useCase.viewTaskLogs(validId) } throws TaskIDNotFoundException
        uiController. viewTaskLogsUIController()

        assert(output.contains(TASK_ID_NOT_FOUND))
    }
}