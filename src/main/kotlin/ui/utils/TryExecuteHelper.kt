package ui.utils

fun <T> tryToExecute(action: () -> T, onSuccess: (result: T) -> Unit) {
    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        getErrorMessageByException(exception).printAsAFailState()
    }
}

fun <T> tryToExecute(
    action: () -> T,
    onSuccess: (result: T) -> Unit,
    onError: (exception: Exception) -> Unit
) {
    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        getErrorMessageByException(exception).printAsAFailState()
        onError(exception)
    }
}


