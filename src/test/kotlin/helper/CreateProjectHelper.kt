package logic.usecases.project.helper

import kotlinx.datetime.LocalDate
import logic.entities.Change
import logic.entities.Project
import logic.entities.State
import logic.entities.Task
import java.util.*

fun createProject(
    id: UUID,
    name: String,
    allowedStates: List<State>,
    tasks: List<Task>,
    changes: List<Change>
) = Project(
    id = id,
    name = name,
    allowedStates = allowedStates,
    tasks = tasks,
    changes = changes
)

