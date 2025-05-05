package ui

import di.appModule
import logic.usecases.LoginUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.controller.CreateTaskUIController
import ui.controllers.CreateProjectUIController
import ui.controllers.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.project.GetProjectUIController
import ui.project.ViewProjectHistoryUIController


fun main() {
    startKoin{
        modules(
            appModule
        )
    }

    val loginUseCase: LoginUseCase = getKoin().get()
    val createProjectUIController: CreateProjectUIController = getKoin().get()
    val deleteTaskUiController: DeleteProjectUiController = getKoin().get()
    val viewProjectHistoryUIController: ViewProjectHistoryUIController = getKoin().get()
    val updateStateUiController: UpdateStateUiController = getKoin().get()
    val getProjectUIController: GetProjectUIController = getKoin().get()
    val deleteProjectUiController: DeleteProjectUiController = getKoin().get()
    val editTaskUiController: EditTaskUiController = getKoin().get()
    val viewTaskLogsUIController: ViewTaskLogsUIController = getKoin().get()
    val createTaskUIController: CreateTaskUIController = getKoin().get()



    val adminHandler: AdminMenuHandler = AdminMenuHandler(
        mapOf(
            1 to createProjectUIController,
            2 to updateStateUiController
        )
    )

    val mateHandler: MateMenuHandler = MateMenuHandler(
        mapOf(
            1 to getProjectUIController,
        )
    )



    val loginUIController = LoginUIController(loginUseCase, adminHandler, mateHandler)

    loginUIController.execute()
}
