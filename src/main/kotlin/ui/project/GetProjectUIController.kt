package ui.project


import logic.usecases.project.GetProjectDetailsUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView

class GetProjectUIController(
    private val getProjectsUseCase: GetProjectDetailsUseCase,
    private val consoleIO: ConsoleIO
):BaseUIController {

    override fun execute(){
        consoleIO.println("Enter Project ID :)")
        val id = consoleIO.readFromUser()
        tryToExecute({
            getProjectsUseCase(id)
        },{
            printSwimlanesView(it)
        })
    }

}