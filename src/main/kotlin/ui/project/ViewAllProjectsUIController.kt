package ui.project

import logic.usecases.project.GetAllProjectsUseCase
import logic.usecases.utils.StateManager
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import ui.controller.BaseUIController
import ui.logs.ViewProjectHistoryUIController
import ui.logs.ViewTaskLogsUIController
import ui.menuHandler.mainMenuProjectsForAdmin
import ui.menuHandler.mainMenuProjectsForMate
import ui.task.ViewAllTaksByProjectIdUIController
import ui.utils.tryToExecute
import ui.utils.printSwimlanesView

class ViewAllProjectsUIController(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val stateManager : StateManager,
) : BaseUIController {
    override suspend fun execute() {
        tryToExecute(
            action = { getAllProjectsUseCase.execute() },
            onSuccess = { projects ->
                projects.forEach { project ->
                    printSwimlanesView(project)
                }
                if(stateManager.getLoggedInUser().isAdmin){
                    startProjectAdminMenu()
                }else {
                    startProjectMateMenu()
                }
            }
        )

    }

    private suspend fun startProjectAdminMenu() {
        mainMenuProjectsForAdmin(
            stateManager.getLoggedInUser(),
            onCreateProject = {
                val createProjectUIController: CreateProjectUIController = CreateProjectUIController(getKoin().get(), getKoin().get())
                createProjectUIController.execute()
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
            },
            onViewProjectLogs = {
                val projectsLogUiController: ViewProjectHistoryUIController = ViewProjectHistoryUIController(getKoin().get(), getKoin().get())
                projectsLogUiController.execute()
            },

        )
    }

    private suspend fun startProjectMateMenu() {
        mainMenuProjectsForMate(
            stateManager.getLoggedInUser(),
            onViewProject = {
                val viewAllTasksByProjectIdUIController: ViewAllTaksByProjectIdUIController = getKoin().get()
                viewAllTasksByProjectIdUIController.execute()
            },
            onViewProjectLogs = {
                val projectsLogUiController: ViewProjectHistoryUIController = getKoin().get()
                projectsLogUiController.execute()
            },

        )
    }
}