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
        consoleIO.println("Enter project ID :)")
        val id = consoleIO.readFromUser()
        consoleIO.println("Enter  State :)")
        val oldState = consoleIO.readFromUser()

        tryToExecute(
            action = { deleteStateUseCase(id, oldState) },
            onSuccess = { consoleIO.println("State Delete Successfully") })

    }


}