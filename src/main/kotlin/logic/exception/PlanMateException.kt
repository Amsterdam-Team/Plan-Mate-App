package logic.exception

sealed class PlanMateException : Exception() {
    sealed class AuthorizationException : PlanMateException() {
        data object AdminPrivilegesRequired : AuthorizationException()
        data object UserNotFound : AuthorizationException()

    }

    sealed class ValidationException : PlanMateException() {
        data object InvalidUsername : ValidationException()
        data object InvalidPassword : ValidationException()
        data object InvalidStateName : ValidationException()
        data object InvalidTaskName : ValidationException()
        data object InvalidTaskID : ValidationException()
        data object InvalidProjectName : ValidationException()
        data object InvalidProjectID : ValidationException()
    }

    sealed class ParsingException : PlanMateException() {
        data object CsvFormatException : ParsingException()
    }

    sealed class NotFoundException : PlanMateException() {
        data object ProjectNotFoundException : ParsingException()
        data object TaskNotFoundException : ParsingException()
        data object StateNotFoundException : ParsingException()
    }

}