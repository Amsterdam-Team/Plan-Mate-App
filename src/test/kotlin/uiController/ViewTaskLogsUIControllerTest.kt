package uiController

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDateTime
import logic.entities.LogItem
import logic.exception.PlanMateException.NotFoundException.TaskIDNotFoundException
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.ViewTaskLogsUIController
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.UUID

class ViewTaskLogsUIControllerTest {
    private lateinit var useCase : ViewTaskLogsUseCase
    private lateinit var uiController : ViewTaskLogsUIController
    private val taskLogs = listOf<LogItem>(
        LogItem(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            message = "This task name is updated by Hend at 12:30 29-4-2025",
            date = LocalDateTime(2025, 4, 29, 8, 30),
            entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        ),
        LogItem(
            id = UUID.fromString("11111551-22a1-bb11-1111-177588lkhjk1"),
            message = "This task state is updated by Hend at 01:30 29-4-2025",
            date = LocalDateTime(2025, 4, 29, 8, 30),
            entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        )
    )

    @BeforeEach
    fun setup(){
        useCase = mockk()
        uiController = ViewTaskLogsUIController(useCase)
    }

    @Test
    fun `should print logs when task id is valid format of UUID and logs are found`(){
        val id = "123e4567-e89b-12d3-a456-426614174000"
        System.setIn(ByteArrayInputStream(id.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        every { useCase.viewTaskLogs(UUID.fromString(id)) } returns taskLogs
        uiController. viewTaskLogsUIController()

        val output = outputStream.toString()
        assert(output.contains(taskLogs.toString()))

    }

    @Test
    fun `should print TaskIDNotFound message when task id is valid format of UUID but not found`(){
        val id = "123e4567-e89b-12d3-a456-426614174000"
        System.setIn(ByteArrayInputStream(id.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        every { useCase.viewTaskLogs(UUID.fromString(id)) } throws TaskIDNotFoundException
        uiController. viewTaskLogsUIController()

        val output = outputStream.toString()
        assert(output.contains(TASK_ID_NOT_FOUND))

    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123e4567-e89b-12d3-a456" ,               //incomplete id
            " ",
            "123e4567-e89b-12d3-a456-426614174%@0" ,  //has special character
            "kahdjshuffl123"                          //invalid format
        ]
    )
    fun `should print wrong id format message when task id is invalid UUID format`(id:String){
        System.setIn(ByteArrayInputStream(id.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        uiController. viewTaskLogsUIController()

        val output = outputStream.toString()
        assert(output.contains(WRONG_ID_FORMAT))
    }

    companion object{
        const val TASK_ID_NOT_FOUND = " This ID not found, Please enter correct id.."
        const val WRONG_ID_FORMAT = "This Id is invalid format , Please ensure you enter correct format of id "
    }
}