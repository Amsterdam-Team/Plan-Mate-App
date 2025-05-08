package helper

import logic.entities.Project
import logic.entities.Task
import logic.entities.User
import java.util.UUID

object TestDataFactory {

    fun createUser(
        id: UUID = UUID.randomUUID(),
        username: String = "mohammad",
        password: String = "123456",
        isAdmin: Boolean = false
    ): User {
        return User(
            id = id,
            username = username,
            password = password,
            isAdmin = isAdmin
        )
    }

    fun createProject(
        id: UUID = UUID.randomUUID(),
        name: String = "Test Project",
        states: List<String> = listOf("TODO", "In Progress"),
        tasks: List<Task> = emptyList()
    ): Project {
        return Project(
            id = id,
            name = name,
            states = states,
            tasks = tasks
        )
    }
}