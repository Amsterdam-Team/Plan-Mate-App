package ui.utils

import ui.utils.DisplayUtils.printError

suspend fun <T> tryToExecute(action: suspend () -> T,  onSuccess: suspend (result: T) -> Unit) {
    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        printError(getErrorMessageByException(exception))
    }
}
    suspend fun <T> tryToExecute(
        action: suspend () -> T,
        onSuccess: (result: T) -> Unit,
        onError: suspend (exception: Exception) -> Unit
    ) {
        try {
            action().also { onSuccess(it) }
        } catch (exception: Exception) {
            printError(getErrorMessageByException(exception))
            onError(exception)
        }
    }


