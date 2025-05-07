package ui

import di.appModule
import logic.usecases.LoginUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO
import ui.task.CreateTaskUIController
import ui.project.CreateProjectUIController
import ui.controllers.CreateStateUIController
import ui.controllers.CreateUserUIController
import ui.controllers.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.project.DeleteProjectUiController
import ui.project.EditProjectUIController
import ui.project.GetProjectUIController
import ui.project.ViewAllProjectsUIController
import ui.project.ViewProjectHistoryUIController
import ui.task.DeleteTaskUIController
import ui.task.EditTaskUiController


fun main() {
    startKoin{
        modules(
            appModule
        )
    }

    val consoleIO : ConsoleIO = getKoin().get()

    val loginUseCase: LoginUseCase = getKoin().get()

    val viewAllProjectsUIController: ViewAllProjectsUIController = getKoin().get()
    val createProjectUIController: CreateProjectUIController = getKoin().get()
    val deleteTaskUiController: DeleteTaskUIController = getKoin().get()
    val viewProjectHistoryUIController: ViewProjectHistoryUIController = getKoin().get()
    val updateStateUiController: UpdateStateUiController = getKoin().get()
    val getProjectUIController: GetProjectUIController = getKoin().get()
    val deleteProjectUiController: DeleteProjectUiController = getKoin().get()
    val editProjectUIController: EditProjectUIController = getKoin().get()
    val createTaskUIController: CreateTaskUIController = getKoin().get()
    val editTaskUIController: EditTaskUiController = getKoin().get()
    val createUserUIController: CreateUserUIController =getKoin().get()
    val createStateUIController:CreateStateUIController =getKoin().get()

    val adminHandler = AdminMenuHandler(
        mapOf(
            1 to viewAllProjectsUIController,
            2 to createTaskUIController,
            3 to editTaskUIController,
            4 to deleteTaskUiController,
            5 to getProjectUIController,//will show all tasks in this project by swim lanes
            6 to viewProjectHistoryUIController,
            7 to createUserUIController,
            8 to createProjectUIController,
            9 to editProjectUIController,
            10 to deleteProjectUiController,
            11 to createStateUIController,
            12 to updateStateUiController,
        )
    )

//
//    ================================
//    Mate Main Menu
//    ================================
//    1. View All Projects
//    2. View State
//            3. Create Task
//            4. Edit Task
//            5. Delete Task
//            6. View Tasks
//            7. View Project Audit History
//            8. View Task Audit History
//            0. Exit
//    """.trimIndent()


    val mateHandler = MateMenuHandler(
        mapOf(
            1 to viewAllProjectsUIController,
            2 to createTaskUIController,
            3 to editTaskUIController,
            4 to deleteTaskUiController,
            5 to getProjectUIController,
            6 to viewProjectHistoryUIController,
        )
    )



    val loginUIController = LoginUIController(loginUseCase, adminHandler, mateHandler, consoleIO)

    loginUIController.execute()
}
