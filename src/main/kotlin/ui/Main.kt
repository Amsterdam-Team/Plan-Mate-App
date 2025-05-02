package ui

import di.appModule
import logic.usecases.LoginUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
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



    val adminHandler: AdminMenuHandler = AdminMenuHandler(
        mapOf(
//            1 to ViewAllProject,
//            2 to viewState
//            3 to createTask
            4 to editTaskUiController,
            5 to deleteTaskUiController,
//            6 to viewTasks,
            7 to viewProjectHistoryUIController,
            8 to viewTaskLogsUIController,
//            9 to createUser,
            10 to createProjectUIController,
            11 to updateStateUiController,  // todo:// need better naming
            12 to deleteProjectUiController,
//            13 to addProjectState
            14 to editTaskUiController, // edit
//            15 to deleteProjectState
        )
    )

    val mateHandler: MateMenuHandler = MateMenuHandler(
        mapOf(
//            1 to viewAllProject,
//          2 to viewTaskState,
//            3 to createTask,
            4 to editTaskUiController,
            5 to deleteTaskUiController,
//            6 to viewAllTask,
            7 to viewProjectHistoryUIController,
            8 to viewTaskLogsUIController


        )
    )



    val loginUIController = LoginUIController(loginUseCase, adminHandler, mateHandler)

    loginUIController.execute()
}
