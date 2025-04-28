package logic.entities

import java.util.*

data class Project(
    val id: UUID,
    val name: String,
    val currentStates: List<State>,
    val tasks: List<Task>,
)