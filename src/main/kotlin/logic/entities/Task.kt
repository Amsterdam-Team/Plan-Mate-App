package logic.entities

import java.util.*

data class Task(
    val id: UUID,
    val name: String,
    val projectId: UUID,
    val state: String,
)