package helper

import kotlinx.datetime.LocalDateTime
import logic.entities.LogItem
import java.util.*

object LogTestFactory {

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

}