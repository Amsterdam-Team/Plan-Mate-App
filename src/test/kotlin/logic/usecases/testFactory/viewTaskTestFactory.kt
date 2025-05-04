package logic.usecases.testFactory

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
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        message = "This task state is updated by Hend at 01:30 29-4-2025",
        date = LocalDateTime(2025, 4, 29, 8, 30),
        entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
    )
)

fun simulateConsoleInteraction(input: String, block: () -> Unit): String {
    val inputStream = ByteArrayInputStream(input.toByteArray())
    val outputStream = ByteArrayOutputStream()

    val originalIn = System.`in`
    val originalOut = System.out

    System.setIn(inputStream)
    System.setOut(PrintStream(outputStream))

    try {
        block()
    } finally {
        System.setIn(originalIn)
        System.setOut(originalOut)
    }

    return outputStream.toString()
}


val validId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
val invalidId = "123e4567-e89b-12d3-a456-426614174%@0"

const val TASK_NOT_FOUND = "Task not found. Please make sure the task ID is correct."
const val INVALID_ID_FORMAT = "This Id is invalid format , Please ensure you enter correct format of id"
