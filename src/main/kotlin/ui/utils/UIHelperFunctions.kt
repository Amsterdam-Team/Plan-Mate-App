package ui.utils

import logic.exception.PlanMateException

fun String.printAsASuccessState() {
    val green = "\u001B[32m"
    val reset = "\u001B[0m"
    println("$green$this$reset")
}

fun String.printAsAFailState() {
    val red = "\u001b[31m"
    val reset = "\u001B[0m"
    println("$red$this$reset")
}

fun getErrorMessageByException(exception: Exception): String {

    return when (exception) {
        is PlanMateException.AuthorizationException.AdminPrivilegesRequiredException ->
            "You need admin privileges to perform this action."

        is PlanMateException.AuthorizationException.WrongPasswordException ->
               "The password you entered is wrong , Please enter correct password"

        is PlanMateException.AuthorizationException.WrongUsernameException ->
            "The user name you entered is wrong , Please enter correct user name"

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