package helper


import helper.TaskFactory.TASK_1
import helper.TaskFactory.TASK_2
import helper.UserFactory.createUser
import logic.entities.Project
import logic.entities.Task
import logic.entities.User
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
    val PROJECT1_ID: UUID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")

    val PROJECT_1 = Project(
        id = UUID.randomUUID(),
        name = "Gym App",
        states = listOf("TODO", "IN_PROGRESS", "DONE"),
        tasks = listOf(TASK_1, TASK_2)
    )

    val validProjectId: UUID = UUID.randomUUID()

    fun validProject(): Project {
        return data.repository.project.helper.createProject(
            id = validProjectId,
            name = "Test Project",
            states = listOf("TODO", "IN_PROGRESS"),
            tasks = emptyList()
        )
    }

    fun adminUser(): User {
        return createUser(
            isAdmin = true,
            username = "admin",
            password = "admin123"
        )
    }



    val mateUser = adminUser().copy(isAdmin = false)
    const val EXISTING_PROJECT_ID: String = "123e4567-e89b-12d3-a456-426614174000"
    const val INVALID_PROJECT_ID: String = "xyz"

    private val EXPECTED_PROJECT_STATES: List<String> = listOf("to-do", "in progress", "done")

    val dummyProject = Project(
        id = UUID.fromString(EXISTING_PROJECT_ID),
        name = "PlanMate",
        states = EXPECTED_PROJECT_STATES,
        tasks = emptyList()
    )
    const val EXPECTED_TASK_STATE = "done"
    const val EXISTING_TASK_ID = "123e4567-e89b-12d3-a456-426614174000"

    val notExistingTaskID = UUID.randomUUID().toString()

    const val INVALID_TASK_ID = "xyz"

    val existingTask = Task(
        id = UUID.fromString(EXISTING_TASK_ID),
        name = "Add View State Feature",
        projectId =  UUID.fromString(EXISTING_PROJECT_ID),
        state = EXPECTED_TASK_STATE
    )


}