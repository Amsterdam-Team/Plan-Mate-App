package ui.controller.task

import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO
import ui.controller.base.BaseUIController
import ui.utils.DisplayUtils
import ui.utils.mainMenuTasks
import ui.utils.printTasksSwimlanesView
import ui.utils.tryToExecute

class ViewTasksByProjectIdUIController(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val consoleIO: ConsoleIO

) : BaseUIController {


    override fun execute() {

        DisplayUtils.printTitle("Task screen ")

        tryToExecute(
            action = {
                DisplayUtils.promptInput("Enter Task ID:")
                val taskId = consoleIO.readFromUser()
                getAllTasksByProjectIdUseCase(taskId)
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