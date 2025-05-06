package ui.utils

import logic.exception.PlanMateException

sealed class ResultStatus<out T> {
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val exception: PlanMateException) : ResultStatus<Nothing>()
}