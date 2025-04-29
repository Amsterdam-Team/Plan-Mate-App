package uiController

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.TaskIDNotFoundException
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.ViewTaskLogsUIController
import utils.TASK_ID_NOT_FOUND
import utils.WRONG_ID_FORMAT
import utils.readInputFromConsole
import utils.taskLogs
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
    fun `should print logs when task id is valid format of UUID and logs are found`(){
        val id = "123e4567-e89b-12d3-a456-426614174000"

        val output = readInputFromConsole(id)

        every { useCase.viewTaskLogs(UUID.fromString(id)) } returns taskLogs()
        uiController. viewTaskLogsUIController()

        assert(output.contains(taskLogs().toString()))
    }

    @Test
    fun `should print TaskIDNotFound message when task id is valid format of UUID but not found`(){
        val id = "123e4567-e89b-12d3-a456-426614174000"

        val output = readInputFromConsole(id)

        every { useCase.viewTaskLogs(UUID.fromString(id)) } throws TaskIDNotFoundException
        uiController. viewTaskLogsUIController()

        assert(output.contains(TASK_ID_NOT_FOUND))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123e4567-e89b-12d3-a456" ,
            " ",
            "123e4567-e89b-12d3-a456-426614174%@0" ,
            "kahdjshuffl123"
        ]
    )
    fun `should print wrong id format message when task id is invalid UUID format`(id:String){

        val output = readInputFromConsole(id)

        uiController. viewTaskLogsUIController()

        assert(output.contains(WRONG_ID_FORMAT))
    }
}