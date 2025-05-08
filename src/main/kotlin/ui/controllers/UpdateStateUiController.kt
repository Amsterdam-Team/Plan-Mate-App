package ui.controllers

import logic.usecases.state.UpdateStateUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class UpdateStateUiController(
    private val editStateUseCase: UpdateStateUseCase,
    private val consoleIO: ConsoleIO
) :BaseUIController{



    override suspend  fun execute(){
        consoleIO.println("Enter project ID :)")
        val id = consoleIO.readFromUser()
        consoleIO.println("Enter Current State :)")
        val oldState = consoleIO.readFromUser()
        consoleIO.println("Enter New State :)")
        val newState = consoleIO.readFromUser()
        tryToExecute(action = { editStateUseCase.updateState(id,oldState,newState)},
            onSuccess = { consoleIO.println("State Updated Successfully")})

    }


}
