package data.repository.project.helper


import logic.entities.Project
import logic.entities.Task
import java.util.*

fun createProject(
    id: UUID,
    name: String,
    states: List<String>,
    tasks: List<Task>,
) = Project(
    id = id,
    name = name,
    states = states,
    tasks = tasks,
)

