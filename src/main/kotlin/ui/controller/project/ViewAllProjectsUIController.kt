package ui.controller.project

import logic.usecases.project.GetAllProjectsUseCase
import ui.controller.base.BaseUIController
import ui.utils.tryToExecute
import ui.utils.printSwimlanesView

class ViewAllProjectsUIController(
    private val getAllProjectsUseCase: GetAllProjectsUseCase
): BaseUIController {
    override fun execute() {
        tryToExecute (
            action = {getAllProjectsUseCase.execute()},
            onSuccess = { projects ->
                projects.forEach { project ->
                    printSwimlanesView(project)
                }
            }
        )
    }
}