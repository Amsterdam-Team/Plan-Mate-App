package helper

import logic.entities.Task
import java.util.*

object CreateTaskTestFactory {

    private const val EXISTING_PROJECT_ID = "123e4567-e89b-12d3-a456-426614174000"
    private const val UN_EXISTING_PROJECT_ID = "123e4567-e89b-12d3-a456-426614174000"
    private const val EXISTING_STATE = "TO-DO"

    const val INVALID_TASK_NAME = "  "
    const val INVALID_PROJECT_ID = "invalid-uuid"
    const val INVALID_STATE = "??"
    const val NON_EXISTENT_STATE = "DONE"

    val validTask = createTask(name = "Add Create Task Feature", projectID = EXISTING_PROJECT_ID, state = EXISTING_STATE)
    val taskWithUnExistingProjectID = createTask(name = "Add Create Task Feature", projectID = UN_EXISTING_PROJECT_ID, state = EXISTING_STATE)

    val existingStates = listOf("TO-DO", "IN-PROGRESS")

    private fun createTask(name: String = "", projectID: String = "", state: String = ""): Task {

        return Task(
            id = UUID.randomUUID(),
            name = name,
            projectId = UUID.fromString(projectID),
            state = state,
        )
    }
}