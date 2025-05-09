package helper

import helper.ConstantsFactory.EXISTING_PROJECT_ID
import helper.TaskFactory.TASK_1
import helper.TaskFactory.TASK_2
import logic.entities.Project
import logic.entities.Task
import java.util.UUID

object ProjectFactory {

    fun createProject(
        id: UUID = UUID.randomUUID(),
        name: String = "",
        states: List<String> = emptyList(),
        tasks: List<Task> = emptyList(),
    ) = Project(
        id = id, name = name, states = states, tasks = tasks
    )

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
        states = listOf("", "to do", "done")
    )

    val foodModeProjectId: UUID = UUID.fromString("ddb0f2e3-1825-40c4-928d-afd4c01d9839")
    val planeMateProjectId: UUID = UUID.fromString("083a5892-d719-430b-8083-7a890f6dc728")
    val PROJECT1_ID: UUID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
    val validProjectId: UUID = UUID.randomUUID()

    val someProjects = listOf<Project>(
        createProject(
            id = foodModeProjectId,
            name = "food mode",
            states = listOf("todo", "in progress", "done", "in review"),
            tasks = listOf(
                Task(
                    id = UUID.randomUUID(),
                    name = "add auth functionality",
                    projectId = foodModeProjectId,
                    state = "todo"
                )
            )
        ),
        createProject(
            id = planeMateProjectId,
            name = "food mode",
            states = listOf("todo", "in progress", "done", "in review"),
            tasks = listOf(
                Task(
                    id = UUID.randomUUID(),
                    name = "add auth functionality",
                    projectId = planeMateProjectId,
                    state = "todo"
                )
            )
        )
    )

    val PROJECT_1 = Project(
        id = UUID.randomUUID(),
        name = "Gym App",
        states = listOf("TODO", "IN_PROGRESS", "DONE"),
        tasks = listOf(TASK_1, TASK_2)
    )

    val dummyProject = Project(
        id = UUID.fromString(EXISTING_PROJECT_ID),
        name = "PlanMate",
        states = listOf("to-do", "in progress", "done"),
        tasks = emptyList()
    )

    fun validProject(): Project {
        return data.repository.project.helper.createProject(
            id = validProjectId,
            name = "Test Project",
            states = listOf("TODO", "IN_PROGRESS"),
            tasks = emptyList()
        )
    }
}