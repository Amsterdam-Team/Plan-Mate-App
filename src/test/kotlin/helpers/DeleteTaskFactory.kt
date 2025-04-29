package helpers

import logic.entities.Project
import logic.entities.Task
import logic.entities.User
import java.util.UUID

object DeleteTaskTestFactory {

    val USER = User(
        username = "mina",
        id = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
        password = "",
        isAdmin = false
    )

    val PROJECT_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")

    val TASK_1 = Task(
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        name = "Fix UI bug",
        projectId = PROJECT_ID,
        state = "IN_PROGRESS"
    )

    val TASK_2 = Task(
        id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
        name = "Write unit tests",
        projectId = PROJECT_ID,
        state = "PENDING"
    )

    val TASK_3 = Task(
        id = UUID.fromString("33333333-3333-3333-3333-333333333333"),
        name = "Deploy to production",
        projectId = PROJECT_ID,
        state = "DONE"
    )

    val PROJECT_1 = Project(
        id = UUID.randomUUID(),
        name = "Gym App",
        states = listOf("TODO", "IN_PROGRESS", "DONE"),
        tasks = listOf(TASK_1, TASK_2)
    )

    val PROJECT_2 = Project(
        id = UUID.randomUUID(),
        name = "Smart Home",
        states = listOf("NEW", "ACTIVE", "ARCHIVED"),
        tasks = listOf(TASK_3)
    )

    val ALL_PROJECTS = listOf(PROJECT_1, PROJECT_2)
}
