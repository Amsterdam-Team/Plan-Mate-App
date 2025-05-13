package ui.state

import logic.usecases.state.DeleteStateUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class DeleteStateUiController(
    private val deleteStateUseCase: DeleteStateUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {


    override suspend fun execute() {
        consoleIO.println(ENTER_PROJECT_ID_MESSAGE)
        val id = consoleIO.readFromUser()
        consoleIO.println(ENTER_STATE_MESSAGE)
        val oldState = consoleIO.readFromUser()

        tryToExecute(
            action = { deleteStateUseCase(id, oldState) },
            onSuccess = { consoleIO.println(DELETE_STATE_MESSAGE) })

    }

    companion object{
        const val ENTER_PROJECT_ID_MESSAGE = "Enter project ID :)"
        const val ENTER_STATE_MESSAGE = "Enter  State :)"
        const val DELETE_STATE_MESSAGE = "State Delete Successfully"
    }
}