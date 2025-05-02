package ui

import logic.usecases.project.DeleteProjectUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class DeleteProjectUiController(val deleteProjectUseCase: DeleteProjectUseCase, val consoleIO: ConsoleIO): BaseUIController {
    override fun execute() {
        val projectId = getProjectId()
        tryToExecute<Boolean>(
           action =  {action(projectId)},
            onSuccess = {onSuccess()}
        )
    }

    private fun action(id: String): Boolean{
        return deleteProjectUseCase.deleteProject(projectId = id)
    }
    private fun getProjectId(): String{
        consoleIO.println("please enter project id here: ")
        val projectId = consoleIO.readFromUser()
        while (true){
            if (projectId.isNotBlank() && projectId.isNotEmpty()){
                return projectId
            }
        }
    }
    private fun onSuccess(){
        consoleIO.println("Project deleted successfully")
    }


}