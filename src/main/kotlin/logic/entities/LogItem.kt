package logic.entities

import java.time.LocalDateTime
import java.util.*

data class LogItem(
    val id: UUID,
    val message: String,
    val date: LocalDateTime,
    val entityId: UUID
)