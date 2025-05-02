package ui.project


import logic.usecases.project.GetProjectsUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView

class GetProjectUIController(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val consoleIO: ConsoleIO
):BaseUIController {

    override fun execute(){
        consoleIO.println("Enter Project Id :)")
        val projectID = consoleIO.readFromUser()
        tryToExecute(action = { getProjectsUseCase.getProject(projectID) },
            onSuccess = { printSwimlanesView(it) /*consoleIO.println(it.toString())*/})
    }


}