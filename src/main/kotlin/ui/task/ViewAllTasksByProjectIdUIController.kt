package ui.task

import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO

import ui.controller.BaseUIController
import ui.utils.mainMenuTasks
import ui.utils.printTasksSwimlanesView
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSubTitle
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class ViewAllTasksByProjectIdUIController(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val consoleIO: ConsoleIO

) : BaseUIController {


    override suspend fun execute() {

        printSubTitle(SUBTITLE_MESSAGE)

        tryToExecute(
            action = {
                promptInput(TASK_ID_PROMPT_MESSAGE)
                val uuid = consoleIO.readFromUser()
                getAllTasksByProjectIdUseCase(uuid)
            }, onSuccess = { tasks ->
                printTasksSwimlanesView(tasks)
            }, onError = ::onViewAllTasksFailed
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

    private suspend fun onViewAllTasksFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onViewAllTasksFailed(exception)
        }

    }

    private companion object {
        const val SUBTITLE_MESSAGE = "Here is ur task screen "
        const val TASK_ID_PROMPT_MESSAGE = "Please enter task id here: "
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE =
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"

    }

}