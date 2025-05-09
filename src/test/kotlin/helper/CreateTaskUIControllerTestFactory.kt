package helper

object CreateTaskUIControllerTestFactory {
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
}