package ui

import console.ConsoleIO
import console.ConsoleIoImpl
import logic.usecases.state.EditStateUsecase

class EditStateUiController(
    editStateUsecase: EditStateUsecase,
    consoleIO: ConsoleIO
) {

    fun execute(projectID: String,oldState:String,newString: String){
    }
    fun isValidProjectID(projectID:String){}
    fun isValidStateName(state:String){}
    fun isSameState(oldState: String,newState: String) = oldState == newState
}
