package ui.task

import logic.usecases.task.DeleteTaskUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class DeleteTaskUIController(
    private val deleteTaskUseCase: DeleteTaskUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {
    override suspend fun execute() {
        tryToExecute(
            action = {
                promptInput(TASK_ID_PROMPT_MESSAGE)
                deleteTaskUseCase.execute(consoleIO.readFromUser())
            }, onSuccess = { isTaskDeleted ->
                if (isTaskDeleted) {
                    printSuccess(TASK_DELETED_SUCCESSFULLY_MESSAGE)
                }
            }, onError = ::onDeleteTaskFailed
        )
    }

    private suspend fun onDeleteTaskFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onDeleteTaskFailed(exception)
        }

    }

    private companion object {
        const val TASK_ID_PROMPT_MESSAGE = "Please enter task id here: "
        const val TASK_DELETED_SUCCESSFULLY_MESSAGE = "Task deleted successfully."
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE =
            "Failed to delete task. Please try again.\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"

    }
}