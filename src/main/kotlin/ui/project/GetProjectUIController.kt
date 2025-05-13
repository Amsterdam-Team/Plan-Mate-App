package ui.project


import logic.usecases.project.GetProjectDetailsUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.promptInput
import ui.utils.printSwimlanesView
import ui.utils.tryToExecute

class GetProjectUIController(
    private val getProjectsUseCase: GetProjectDetailsUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        promptInput(PROJECT_ID_PROMPT_MESSAGE)
        val id = consoleIO.readFromUser()
        tryToExecute(
            {
                getProjectsUseCase(id)
            }, {
                printSwimlanesView(it)
            },
            onError = ::onGetProjectFailed
        )
    }

    private suspend fun onGetProjectFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onGetProjectFailed(exception)
        }

    }

    private companion object {
        const val PROJECT_ID_PROMPT_MESSAGE = "Please enter project id here: "
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE = "Enter $RETRY to re enter a new id again or $CANCEL to exit"

    }

}