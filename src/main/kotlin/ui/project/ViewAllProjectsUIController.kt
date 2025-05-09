package ui.project

import logic.usecases.project.GetAllProjectsUseCase
import org.koin.java.KoinJavaComponent.getKoin
import ui.controller.BaseUIController
import ui.menuHandler.mainMenuProjects
import ui.task.ViewAllTaksByProjectIdUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView

class ViewAllProjectsUIController(
    private val getAllProjectsUseCase: GetAllProjectsUseCase
) : BaseUIController {
    override suspend fun execute() {
        tryToExecute(
            action = { getAllProjectsUseCase.execute() },
            onSuccess = { projects ->
                projects.forEach { project ->
                    printSwimlanesView(project)
                }
            }
        )
        startProjectMenu()
    }

    private suspend fun startProjectMenu() {
        mainMenuProjects(
            onCreateProject = {

            },
            onViewProject = {
                val viewAllTasksByProjectIdUIController: ViewAllTaksByProjectIdUIController = getKoin().get()
                viewAllTasksByProjectIdUIController.execute()
            },
            onEditProject = {
                val editProjectUIController: EditProjectUIController = getKoin().get()
                editProjectUIController.execute()

            },
            onDeleteProject = {
                val deleteProjectUiController: DeleteProjectUiController = getKoin().get()
                deleteProjectUiController.execute()
            }
        )
    }
}