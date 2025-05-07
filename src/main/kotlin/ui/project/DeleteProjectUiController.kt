package ui.project

import logic.usecases.project.DeleteProjectUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class DeleteProjectUiController(val deleteProjectUseCase: DeleteProjectUseCase, val consoleIO: ConsoleIO):
    BaseUIController {
    override suspend fun execute() {
        val projectId = getProjectId()
        tryToExecute<Boolean>(
            action = { deleteProjectUseCase.deleteProject(projectId = projectId)},
            onSuccess = { onSuccess(it) }
        )
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
    private fun onSuccess(result: Boolean){
        if (result){
            consoleIO.println("Project deleted successfully")

        }else{
            consoleIO.println("Failed deleting project")
        }
    }


}