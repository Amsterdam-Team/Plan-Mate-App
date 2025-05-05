package ui.utils

import logic.exception.PlanMateException
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.AuthorizationException.UserNotFoundException
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.ParsingException.CsvFormatException
import logic.exception.PlanMateException.ValidationException.*

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
        is AdminPrivilegesRequiredException -> "You need admin privileges to perform this action."


        is PlanMateException.AuthorizationException.WrongPasswordException ->
               "The password you entered is wrong , Please enter correct password"

        is PlanMateException.AuthorizationException.WrongUsernameException ->
            "The user name you entered is wrong , Please enter correct user name"

        is UserNotFoundException -> "User not found. Please check the user ID or try again."

        is InvalidUsernameException -> "The username you entered is not valid. Please try a different one."

        is InvalidPasswordException -> "The password you entered is not valid. Please follow the password rules."

        is InvalidStateNameException -> "The state name is not valid. State name must be not be empty and contain at least 3 characters"

        is InvalidTaskNameException -> "The task name is not valid. Please enter a proper task name."

        is InvalidTaskIDException -> "This Id is invalid format , Please ensure you enter correct format of id"

        is InvalidProjectNameException -> "The project name is not valid. Please enter a valid project name."

        is InvalidProjectIDException -> "The project ID is invalid. Please check and try again."

        is CsvFormatException -> "Something went wrong while handling your data. Please try again."

        is ProjectNotFoundException -> "Project not found. It may have been deleted or doesn't exist."

        is TaskNotFoundException -> "Task not found. Please make sure the task ID is correct."

        is StateNotFoundException -> "State not found. Please check the state name or ID."

        is EmptyProjectNameException -> "Project name shouldn't be empty,Please enter the project name"

        is EmptyProjectStatesException -> "Project states shouldn't be empty,Please enter the project states"


        is SameStateNameException -> "Current state and new state are identical. No changes applied."
        is TaskLogsNotFound -> "This task not have any logs till now..."

        is EmptyDataException -> "You must enter some data, this field cannot be empty"

        is PlanMateException -> "Something went wrong with your request. Please try again."

        else -> "An unexpected error occurred. Please try again later."
    }
}