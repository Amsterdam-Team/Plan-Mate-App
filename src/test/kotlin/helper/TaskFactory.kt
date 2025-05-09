package helper

import helper.ProjectFactory.PROJECT1_ID
import logic.entities.Task
import java.util.UUID

object TaskFactory {
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
    private const val CANCEL = "cancel"

    const val VALID_TASK_NAME = "Test Task"
    const val IN_VALID_TASK_NAME = "!!"

    const val VALID_PROJECT_ID = "123e4567-e89b-12d3-a456-426614174000"
    const val IN_VALID_PROJECT_ID = "xyz"

    const val VALID_STATE = "Done"
    const val IN_VALID_STATE = ":"

    const val TASK_CREATED_SUCCESSFULLY_MESSAGE = "✅ Your Task Created Successfully"

    const val INVALID_TASK_NAME_ERROR_MESSAGE =
        "The task name is not valid. Please enter a proper task name."
    const val INVALID_PROJECT_ID_ERROR_MESSAGE =
        "The project ID is invalid. Please check and try again."
    const val INVALID_STATE_NAME_ERROR_MESSAGE =
        "The state name is not valid. Please enter a valid name."
    const val STATE_NOT_FOUND_ERROR_MESSAGE =
        "State not found. Please check the state name or ID."

    fun getDummyInputs(
        taskName: String = VALID_TASK_NAME,
        projectId: String = VALID_PROJECT_ID,
        taskState: String = VALID_STATE
    ): List<String> {
        return listOf(taskName, projectId, taskState, CANCEL)
    }


    val TASK_1 = Task(
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        name = "Fix UI bug",
        projectId = PROJECT1_ID,
        state = "IN_PROGRESS"
    )

    val TASK_2 = Task(
        id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
        name = "Write unit tests",
        projectId = PROJECT1_ID,
        state = "PENDING"
    )

}