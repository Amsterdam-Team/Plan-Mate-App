package ui.utils

import logic.exception.PlanMateException

fun getErrorMessageByException(exception: Exception): String {

    return when (exception) {
        is PlanMateException.AuthorizationException.AdminPrivilegesRequiredException ->
            "You need admin privileges to perform this action."

        is PlanMateException.AuthorizationException.UserNotFoundException ->
            "User not found. Please check the user ID or try again."

        is PlanMateException.ValidationException.InvalidUsernameException ->
            "The username you entered is not valid. Please try a different one."

        is PlanMateException.ValidationException.InvalidPasswordException ->
            "The password you entered is not valid. Please follow the password rules."

        is PlanMateException.ValidationException.InvalidStateNameException ->
            "The state name is not valid. Please enter a valid name."

        is PlanMateException.ValidationException.InvalidTaskNameException ->
            "The task name is not valid. Please enter a proper task name."

        is PlanMateException.ValidationException.InvalidTaskIDException ->
            "The task ID is invalid. Please check and try again."

        is PlanMateException.ValidationException.InvalidProjectNameException ->
            "The project name is not valid. Please enter a valid project name."

        is PlanMateException.ValidationException.InvalidProjectIDException ->
            "The project ID is invalid. Please check and try again."

        is PlanMateException.ParsingException.CsvFormatException ->
            "Something went wrong while handling your data. Please try again."

        is PlanMateException.NotFoundException.ProjectNotFoundException ->
            "Project not found. It may have been deleted or doesn't exist."

        is PlanMateException.NotFoundException.TaskNotFoundException ->
            "Task not found. Please make sure the task ID is correct."

        is PlanMateException.NotFoundException.StateNotFoundException ->
            "State not found. Please check the state name or ID."

        is PlanMateException -> "Something went wrong with your request. Please try again."

        else -> "An unexpected error occurred. Please try again later."
    }
}