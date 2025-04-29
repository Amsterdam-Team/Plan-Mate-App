package logic.usecases.task.testFactory

import logic.entities.Task
import java.util.*

object CreateTaskTestFactory {

    private val existingProjectID = UUID.fromString("anExistingProjectID")

    private const val EXISTING_STATE = "TO-DO"
    val validTask = createTask(name = "Add Create Task Feature", projectID = existingProjectID, state = EXISTING_STATE)

    val taskWithWrongProjectID = createTask(projectID = UUID.randomUUID())

    private fun createTask(name: String = "", projectID: UUID = UUID.randomUUID(), state: String = ""): Task {

        return Task(
            id = UUID.randomUUID(),
            name = name,
            projectId = projectID,
            state = state,
            )
    }

}