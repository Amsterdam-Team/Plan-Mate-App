package ui.utils

suspend fun <T> tryToExecute(action: suspend () -> T,  onSuccess: suspend (result: T) -> Unit) {
    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        getErrorMessageByException(exception).printAsAFailState()
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
        getErrorMessageByException(exception).printAsAFailState()
        onError(exception)
    }
}


