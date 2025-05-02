package logic.usecases.testFactory

import logic.entities.Task
import java.util.*

object CreateTasksWithProjectID {
    fun createTaskWithProjectID(projectId: UUID) = createTaskWithProjectID(projectID = projectId)
    private fun createTaskWithProjectID(id: String = "", name: String = "", projectID: UUID, state: String = ""): Task {

        return Task(
            id = UUID.randomUUID(),
            name = name,
            projectId = projectID,
            state = state,
        )
    }
}