package ui.task

import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO

import ui.controller.BaseUIController
import ui.utils.mainMenuTasks
import ui.utils.printTasksSwimlanesView
import ui.utils.DisplayUtils
import ui.utils.tryToExecute

class ViewAllTasksByProjectIdUIController(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val consoleIO: ConsoleIO

) : BaseUIController {


    override suspend fun execute() {

        DisplayUtils.printTitle("Task screen ")

        tryToExecute(
            action = {
                DisplayUtils.promptInput("Enter Task ID:")
                val uuid = consoleIO.readFromUser()
                getAllTasksByProjectIdUseCase(uuid)
            },
            onSuccess = { tasks ->
                printTasksSwimlanesView(tasks)
            }
        )
        mainMenuTasks(
            onViewTaskDetails = {
                val viewTaskDetailsUIController: ViewTaskDetailsUIController = getKoin().get()
                viewTaskDetailsUIController.execute()
            },
            onCreateTask = {
                val controller = CreateTaskUIController(createTaskUseCase, consoleIO)
                controller.execute()
            },


            )
    }

}