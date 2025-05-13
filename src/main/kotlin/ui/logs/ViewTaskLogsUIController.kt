package ui.logs

import logic.usecases.logs.ViewTaskLogsUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class ViewTaskLogsUIController(
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        promptInput(TASK_ID_PROMPT_MESSAGE)
        val taskId = consoleIO.readFromUser()
        tryToExecute(
            action = { viewTaskLogsUseCase.viewTaskLogs(taskId) }, onSuccess = { logs ->
            printSuccess(SUCCESS_VIEW_TASKS_MESSAGE)
            logs.forEach { consoleIO.println(it.message) }
        }, onError = ::onViewTasksLogsFailed
        )
    }

    private suspend fun onViewTasksLogsFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onViewTasksLogsFailed(exception)
        }

    }

    private companion object {
        const val TASK_ID_PROMPT_MESSAGE = "Enter Task ID :"
        const val SUCCESS_VIEW_TASKS_MESSAGE = "Here is all ur tasks logs with that id"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE = "Enter $RETRY to re enter a new id again or $CANCEL to exit"
    }
}