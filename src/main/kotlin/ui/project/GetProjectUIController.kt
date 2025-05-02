package ui.project


import logic.usecases.project.GetProjectUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView

class GetProjectUIController(
    private val getProjectUseCase: GetProjectUseCase,
    private val consoleIO: ConsoleIO
):BaseUIController {

    override fun execute(){
        consoleIO.println("Enter Project Id :)")
        val projectID = consoleIO.readFromUser()
        tryToExecute(action = { getProjectUseCase.getProject(projectID) },
            onSuccess = { printSwimlanesView(it) /*consoleIO.println(it.toString())*/})
    }


}