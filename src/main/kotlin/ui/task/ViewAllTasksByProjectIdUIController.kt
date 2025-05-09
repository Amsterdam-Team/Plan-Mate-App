package ui.task

import console.ConsoleIO
import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import org.koin.java.KoinJavaComponent.getKoin

import ui.controller.BaseUIController
import ui.controller.CreateTaskUIController
import ui.menuHandler.mainMenuTasks
import ui.swimlane.printTasksSwimlanesView
import ui.utils.DisplayUtils
import ui.utils.tryToExecute

class ViewAllTaksByProjectIdUIController(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val consoleIO: ConsoleIO

) : BaseUIController {


    override suspend fun execute() {

        DisplayUtils.printTitle("Task screen ")

        tryToExecute(
            action = {
                DisplayUtils.promptInput("Enter project ID:")
                val uuid = consoleIO.readFromUser()
                getAllTasksByProjectIdUseCase(uuid)
            },
            onSuccess = { tasks ->
                printTasksSwimlanesView(tasks)
            }
        )
        mainMenuTasks(
            onViewTaskDetails = {
                val ViewTaskDetailsUIController: ViewTaskDetailsUIController = getKoin().get()
                ViewTaskDetailsUIController.execute()
            },
            onCreateTask = {
                val controller = CreateTaskUIController(createTaskUseCase, consoleIO)
                controller.execute()
            },


            )
    }

}