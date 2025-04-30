package ui

import console.ConsoleIO
import logic.usecases.state.UpdateStateUseCase

class UpdateStateUiController(
    editStateUsecase: UpdateStateUseCase,
    consoleIO: ConsoleIO
) {

    fun execute(projectID: String,oldState:String,newString: String){
    }
    fun isValidProjectID(projectID:String){}
    fun isValidStateName(state:String){}
    fun isSameState(oldState: String,newState: String) = oldState == newState
}
