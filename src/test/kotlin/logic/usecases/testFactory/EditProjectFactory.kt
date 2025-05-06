package logic.usecases.testFactory

import data.repository.project.helper.createProject
import helper.createUser
import logic.entities.Project
import logic.entities.User
import java.util.*

object EditProjectFactory {

    val validProjectId: UUID = UUID.randomUUID()

    fun validProject(): Project {
        return createProject(
            id = validProjectId,
            name = "Test Project",
            states = listOf("TODO", "IN_PROGRESS"),
            tasks = emptyList()
        )
    }

    fun simulateUserInputs(vararg userInputs: String): AutoCloseable {
        val originalSystemIn = System.`in`
        val fakeInputStream = userInputs.joinToString("\n").plus("\n").byteInputStream()
        System.setIn(fakeInputStream)

        return AutoCloseable {
            System.setIn(originalSystemIn)
        }
    }

    fun adminUser(): User {
        return createUser(
            isAdmin = true,
            username = "admin",
            password = "admin123"
        )
    }

    fun projectList(): List<Project> {
        return listOf(
            createProject(UUID.randomUUID(), "Project A", listOf("TODO"), emptyList()),
            createProject(UUID.randomUUID(), "Project B", listOf("IN_PROGRESS"), emptyList())
        )
    }

    val mateUser = adminUser().copy(isAdmin = false)


}