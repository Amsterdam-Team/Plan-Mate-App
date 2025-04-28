package logic.entities

import kotlinx.datetime.LocalDate
import java.util.*

data class Change(
    val id: UUID,
    val message: String,
    val date: LocalDate
)

data class TaskChangeLog(
    val id: UUID,
    val message: String,
    val date: LocalDate,
    val taskId: UUID,
    val doneBy: String  // the name of user

)
data class ProjectChangeLog(
    val id: UUID,
    val message: String,
    val date: LocalDate,
    val projectId: UUID,

    )
