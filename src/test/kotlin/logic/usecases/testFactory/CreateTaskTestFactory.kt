package logic.usecases.testFactory

import logic.entities.Task
import java.util.*

object CreateTaskTestFactory {

    private val existingProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

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