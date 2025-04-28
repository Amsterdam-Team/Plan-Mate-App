package utils

sealed class ResultStatus<out T> {
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val errorMessage: String) : ResultStatus<Nothing>()
    data class Empty(val message: String) : ResultStatus<Nothing>()
}