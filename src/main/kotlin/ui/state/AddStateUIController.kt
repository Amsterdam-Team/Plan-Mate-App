package ui.state

import logic.usecases.state.AddStateUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSubTitle
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class AddStateUIController(
    private val useCase: AddStateUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        printSubTitle(SUBTITLE_MESSAGE)

        promptInput(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser()

        promptInput(STATE_NAME_PROMPT_MESSAGE)
        val state = consoleIO.readFromUser()

        tryToExecute(
            action = { useCase.execute(projectId, state) }, onSuccess = { result ->
                if (result) {
                    printSuccess(STATE_ADDED_SUCCESSFULLY_MESSAGE)
                }
            }, onError = ::onAddStateFailed
        )
    }

    private suspend fun onAddStateFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onAddStateFailed(exception)
        }

    }

    private companion object {
        const val SUBTITLE_MESSAGE = "➕ Add New State to the Project"
        const val PROJECT_ID_PROMPT_MESSAGE = "Please enter project id here: "
        const val STATE_NAME_PROMPT_MESSAGE = "Please enter state name here: "
        const val STATE_ADDED_SUCCESSFULLY_MESSAGE = "A new state added successfully!"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE =
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"

    }
}