package helpers

import logic.entities.Project
import logic.entities.Task
import logic.entities.User
import java.util.*

fun createProject(
    id: UUID,
    name: String,
    states: List<String>,
    tasks: List<Task>
): Project {
    return Project(
        id = id,
        name = name,
        states = states,
        tasks = tasks
    )
}
fun createUser(
    id: UUID = UUID.randomUUID(),
    isAdmin: Boolean = false,
    username: String = "user1",
    password: String = "123456"
): User = User(
    id = id,
    isAdmin = isAdmin,
    username = username,
    password = password
)
