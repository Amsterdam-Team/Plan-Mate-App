package logic.exception

sealed class PlanMateException : Exception() {
    sealed class AuthorizationException : PlanMateException() {
        data object AdminPrivilegesRequiredException : AuthorizationException()
        data object UserNotFoundException : AuthorizationException()

    }

    sealed class ValidationException : PlanMateException() {
        data object InvalidUsernameException : ValidationException()
        data object InvalidPasswordException : ValidationException()
        data object InvalidStateNameException : ValidationException()
        data object InvalidTaskNameException : ValidationException()
        data object InvalidTaskIDException : ValidationException()
        data object InvalidProjectNameException : ValidationException()
        data object InvalidProjectIDException : ValidationException()
    }

    sealed class ParsingException : PlanMateException() {
        data object CsvFormatException : ParsingException()
    }

    sealed class DataSourceException: PlanMateException(){
        data object EmptyFileException: DataSourceException()
        data object ObjectDoesNotExistException: DataSourceException()
        data object EmptyDataException: DataSourceException()

    }

    sealed class NotFoundException : PlanMateException() {
        data object ProjectNotFoundException : ParsingException()
        data object TaskNotFoundException : ParsingException()
        data object StateNotFoundException : ParsingException()
    }

}