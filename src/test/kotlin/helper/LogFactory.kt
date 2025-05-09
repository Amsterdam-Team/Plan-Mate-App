package helper

import helper.ProjectFactory.PROJECT_1
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import java.util.*

object LogFactory {
    private val NOW = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)

    fun createLogItem(
        id: UUID = UUID.randomUUID(),
        message: String = "standard log message",
        entityId: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime(2025, 4, 29, 8, 30)
    ) = LogItem(
        id = id,
        message = message,
        entityId = entityId,
        date = date
    )

    val LOG_1 = LogItem(
        id = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
        message = "Log 1",
        date = NOW,
        entityId = PROJECT_1.id
    )

    val LOG_2 = LogItem(
        id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
        message = "Log 2",
        date = NOW,
        entityId = PROJECT_1.id
    )

    val LOGS_FOR_PROJECT_1 = listOf(LOG_1, LOG_2)

    fun taskLogs() = listOf<LogItem>(
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
}