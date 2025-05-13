package ui.project

import logic.usecases.project.GetAllProjectsUseCase
import ui.controller.BaseUIController
import ui.utils.printSwimlanesView
import ui.utils.tryToExecute

class ViewAllProjectsUIController(
    private val getAllProjectsUseCase: GetAllProjectsUseCase
) : BaseUIController {
    override suspend fun execute() {
        tryToExecute(action = { getAllProjectsUseCase.execute() }, onSuccess = { projects ->
            projects.forEach { project ->
                printSwimlanesView(project)
            }
        })
    }
}