package ui.controller.project


import logic.usecases.project.GetProjectDetailsUseCase
import ui.console.ConsoleIO
import ui.controller.base.BaseUIController
import ui.utils.tryToExecute
import ui.utils.printSwimlanesView

class GetProjectUIController(
    private val getProjectsUseCase: GetProjectDetailsUseCase,
    private val consoleIO: ConsoleIO
): BaseUIController {

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