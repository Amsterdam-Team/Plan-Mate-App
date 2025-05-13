package ui.logs

import logic.usecases.logs.GetProjectHistoryUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.promptInput
import ui.utils.formatLogItem
import ui.utils.tryToExecute

class ViewProjectHistoryUIController(
    private val getProjectHistoryUseCase: GetProjectHistoryUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        tryToExecute(
            action = {
            promptInput(PROJECT_ID_PROMPT_MESSAGE)
            getProjectHistoryUseCase.execute(consoleIO.readFromUser())
        }, onSuccess = {
            it.forEach { consoleIO.println(formatLogItem(it)) }
        }, onError = ::onViewProjectHistoryFailed
        )

    }

    private suspend fun onViewProjectHistoryFailed(exception: Exception) {
        printError(FAIL_TO_GET_PROJECTS_HISTORY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onViewProjectHistoryFailed(exception)
        }

    }

    private companion object {
        const val PROJECT_ID_PROMPT_MESSAGE = "Please Enter Project ID:"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val FAIL_TO_GET_PROJECTS_HISTORY_MESSAGE = "Enter $RETRY to re enter a new id again or $CANCEL to exit"


    }
}