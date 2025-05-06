package ui.main

import di.appModule
import logic.usecases.auth.LoginUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO
import ui.controller.task.CreateTaskUIController
import ui.controller.project.CreateProjectUIController
import ui.controller.auth.LoginUIController
import ui.controller.state.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.controller.project.DeleteProjectUiController
import ui.controller.project.GetProjectUIController
import ui.controller.project.ViewProjectHistoryUIController
import ui.controller.task.ViewTaskLogsUIController


fun main() {
    startKoin{
        modules(
            appModule
        )
    }

    val consoleIO : ConsoleIO = getKoin().get()
    val loginUseCase: LoginUseCase = getKoin().get()
    val createProjectUIController: CreateProjectUIController = getKoin().get()
    val deleteTaskUiController: DeleteProjectUiController = getKoin().get()
    val viewProjectHistoryUIController: ViewProjectHistoryUIController = getKoin().get()
    val updateStateUiController: UpdateStateUiController = getKoin().get()
    val getProjectUIController: GetProjectUIController = getKoin().get()
    val deleteProjectUiController: DeleteProjectUiController = getKoin().get()
    val viewTaskLogsUIController: ViewTaskLogsUIController = getKoin().get()
    val createTaskUIController: CreateTaskUIController = getKoin().get()



    val adminHandler: AdminMenuHandler = AdminMenuHandler(
        mapOf(
            1 to createProjectUIController,
            2 to updateStateUiController,
            12 to deleteProjectUiController
        )
    )

    val mateHandler: MateMenuHandler = MateMenuHandler(
        mapOf(
            1 to getProjectUIController,
        )
    )



    val loginUIController = LoginUIController(loginUseCase, adminHandler, mateHandler, consoleIO)

    loginUIController.execute()
}
