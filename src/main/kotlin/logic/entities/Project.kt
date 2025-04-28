package logic.entities

import java.util.*

data class Project(
    val id: UUID,
    val name: String,
    val allowedStates: List<State>,
    val tasks: List<Task>,
    val changes: List<Change>
)