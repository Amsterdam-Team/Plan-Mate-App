package ui.utils

fun <T> tryToExecute(action: () -> T, onSuccess: (result: T) -> Unit) {
    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        println(getErrorMessageByException(exception))
    }
}


