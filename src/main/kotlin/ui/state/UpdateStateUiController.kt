package ui.state

import logic.usecases.state.UpdateStateUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class UpdateStateUiController(
    private val editStateUseCase: UpdateStateUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {

        promptInput(PROJECT_ID_PROMPT_MESSAGE)
        val id = consoleIO.readFromUser()
        promptInput(CURRENT_STATE_NAME_PROMPT_MESSAGE)
        val oldState = consoleIO.readFromUser()
        promptInput(NEW_STATE_NAME_PROMPT_MESSAGE)
        val newState = consoleIO.readFromUser()

        tryToExecute(
            action = {
                editStateUseCase.updateState(id, oldState, newState)
            }, onSuccess = {
                printSuccess(STATE_UPDATED_SUCCESSFULLY_MESSAGE)
            }, onError = ::onUpdateStateFailed
        )

    }

    private suspend fun onUpdateStateFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onUpdateStateFailed(exception)
        }

    }

    private companion object {
        const val PROJECT_ID_PROMPT_MESSAGE = "Please enter project id here: "
        const val CURRENT_STATE_NAME_PROMPT_MESSAGE = "Please enter current state name here: "
        const val NEW_STATE_NAME_PROMPT_MESSAGE = "Please enter new state name here: "
        const val STATE_UPDATED_SUCCESSFULLY_MESSAGE = "State Updated Successfully"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE =
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"

    }


}
