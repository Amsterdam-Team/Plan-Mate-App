package logic.entities

import kotlinx.datetime.LocalDate
import java.util.*

data class Change(
    val id: UUID,
    val message: String,
    val date: LocalDate
)