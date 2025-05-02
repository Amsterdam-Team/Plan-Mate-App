package logic.usecases.testFactories

import logic.entities.Project
import logic.entities.Task
import java.util.UUID

object CreateProjectTestFactory {
    val validProjectTest = createProject(
        id = UUID.randomUUID(),
        name = "initial Test Project",
        states = listOf("In Progress", "In Review", "Done"),
    )
    val emptyProjectNameTest = createProject(
        id = UUID.randomUUID(),
        states = listOf("In Progress", "In Review", "Done"),
    )
    val inValidProjectNameTest = createProject(
        id = UUID.randomUUID(),
        name = "13515#$%#$",
        states = listOf("In Progress", "In Review", "Done"),
    )
    val emptyProjectStateTest = createProject(
        id = UUID.randomUUID(),
        name = "initial Test Project",
    )


     fun createProject(
        id: UUID = UUID.randomUUID(),
        name: String = "",
        states: List<String> = emptyList(),
        tasks: List<Task> = emptyList(),
    ) = Project(
        id = id, name = name, states = states, tasks = tasks
    )
}