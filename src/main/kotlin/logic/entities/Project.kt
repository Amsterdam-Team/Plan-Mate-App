package logic.entities

import java.util.*

data class Project(
    val id: UUID,
    val name: String,
    val states: List<String>,
    val tasks: List<Task>,
){
    override fun toString(): String {
        return "projectID:$id"

    }
}