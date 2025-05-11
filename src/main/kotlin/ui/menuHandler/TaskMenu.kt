package ui.menuHandler

import ui.controller.BaseUIController

suspend fun mainMenuTasks(
    onCreateTask: suspend () -> Unit,
    onViewTaskDetails: suspend () -> Unit,
    onViewTasksLogs:  suspend ()-> Unit,
) {
    val taskControllers = mapOf(
        1 to object : BaseUIController {
            override suspend fun execute() = onCreateTask()
        },
        2 to object : BaseUIController {
            override suspend fun execute() = onViewTaskDetails()
        },
        3 to object : BaseUIController{
            override suspend fun execute() = onViewTasksLogs()

        }

        )

    val taskMenu = TaskMenuHandler(taskControllers)
    taskMenu.start()
}

