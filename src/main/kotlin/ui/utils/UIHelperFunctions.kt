package ui.utils

import logic.exception.PlanMateException.AuthorizationException.*
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.ParsingException.CsvFormatException
import logic.exception.PlanMateException.ValidationException.*


fun getErrorMessageByException(exception: Exception): String {

    return when (exception) {
        is AdminPrivilegesRequiredException -> "You need admin privileges to perform this action."

        is WrongPasswordException -> "The password you entered is wrong , Please enter correct password"

        is WrongUsernameException -> "The user name you entered is wrong , Please enter correct user name"

        is UserNotFoundException -> "User not found. Please check the user ID or try again."

        is InvalidUsernameException -> "The username you entered is not valid. UserName must not be empty field and should be at least 3 characters. You can use numbers, hyphen, spaces and under scores"

        is InvalidPasswordException -> "The password you entered is not valid. The password must be at least 8 characters length."

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

        is InvalidUUIDFormatException -> "Invalid UUID Format"

        is UnAuthenticatedException -> "Not logged in, please login first."

        is EmptyDataException -> "You must enter some data, this field cannot be empty"

        is ObjectDoesNotExistException -> "No data was found for ur input"

        else -> "An unexpected error occurred. Please try again later."
    }
}