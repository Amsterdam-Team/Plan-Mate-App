package utils.hepler

import kotlinx.datetime.LocalDateTime
import logic.entities.LogItem
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.UUID

fun taskLogs ()= listOf<LogItem>(
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
fun readInputFromConsole(input : String) : String{
    System.setIn(ByteArrayInputStream(input.toByteArray()))

    val outputStream = ByteArrayOutputStream()
    System.setOut(PrintStream(outputStream))

    val output = outputStream.toString()

    return (output)
}

const val TASK_ID_NOT_FOUND = " This ID not found, Please enter correct id.."
const val WRONG_ID_FORMAT = "This Id is invalid format , Please ensure you enter correct format of id "