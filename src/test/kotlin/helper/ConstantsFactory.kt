package helper

import java.util.UUID

object ConstantsFactory {
    const val CANCEL = "cancel"
    const val EMPTY_STRING = ""
    const val BLANK_NAME = "   "

    const val VALID_NAME = "Bilal Al-khatib"
    const val SHORT_NAME = "az"
    val LONG_NAME = "a".repeat(101)
    const val NAME_WITH_INVALID_CHARACTERS = "bilal@x!"
    const val NAME_WITH_UNDERSCORES_AND_SPACES = "Bilal_b Alkhatib-A"

    const val VALID_TASK_NAME = "Test Task"
    const val IN_VALID_TASK_NAME = "!!"
    const val INVALID_TASK_NAME = "  "
    const val EXPECTED_TASK_STATE = "done"
    const val VALID_STATE = "Done"
    const val IN_VALID_STATE = ":"
    const val INVALID_STATE = "??"
    const val NON_EXISTENT_STATE = "DONE"
    const val EXISTING_STATE = "TO-DO"

    const val VALID_PROJECT_ID = "123e4567-e89b-12d3-a456-426614174000"
    const val IN_VALID_PROJECT_ID = "xyz"
    const val INVALID_PROJECT_ID = "invalid-uuid"
    const val EXISTING_PROJECT_ID = "123e4567-e89b-12d3-a456-426614174000"
    const val UN_EXISTING_PROJECT_ID = "123e4567-e89b-12d3-a456-426614174000"
    const val EXISTING_TASK_ID = "123e4567-e89b-12d3-a456-426614174000"
    const val INVALID_TASK_ID = "xyz"
    const val INVALID_UUID_STRING_FORMAT = "XYZ"
    val VALID_UUID = UUID.randomUUID().toString()
    val validId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    val invalidId = "123e4567-e89b-12d3-a456-426614174%@0"

    const val TASK_CREATED_SUCCESSFULLY_MESSAGE = "✅ Your Task Created Successfully"
    const val INVALID_TASK_NAME_ERROR_MESSAGE =
        "The task name is not valid. Please enter a proper task name."
    const val INVALID_PROJECT_ID_ERROR_MESSAGE =
        "The project ID is invalid. Please check and try again."
    const val INVALID_STATE_NAME_ERROR_MESSAGE =
        "The state name is not valid. Please enter a valid name."
    const val STATE_NOT_FOUND_ERROR_MESSAGE =
        "State not found. Please check the state name or ID."
    const val TASK_NOT_FOUND = "Task not found. Please make sure the task ID is correct."
    const val INVALID_ID_FORMAT = "This Id is invalid format , Please ensure you enter correct format of id"
    const val SUCCESS_MESSAGE_FOR_LOGIN = "Success Login......"
    const val WRONG_USER_NAME = "The user name you entered is wrong , Please enter correct user name"
    const val WRONG_PASSWORD = "The password you entered is wrong , Please enter correct password"
}