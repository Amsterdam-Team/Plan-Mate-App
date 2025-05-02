package ui

import console.ConsoleIO
import logic.usecases.state.UpdateStateUseCase

class UpdateStateUiController(
    editStateUsecase: UpdateStateUseCase,
    consoleIO: ConsoleIO
) {

    fun execute(){
    }
    fun isValidProjectID(projectID:String){}
    fun isValidStateName(state:String){}
    fun isSameState(oldState: String,newState: String) = oldState == newState
}
