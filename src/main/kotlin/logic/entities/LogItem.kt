package logic.entities

import kotlinx.datetime.LocalDate
import java.util.*

data class LogItem(
    val id: UUID,
    val message: String,
    val date: LocalDate,
    val entityId : UUID
)