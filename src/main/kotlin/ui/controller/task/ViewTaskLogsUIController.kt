package ui.controller.task

import logic.usecases.task.ViewTaskLogsUseCase
import ui.console.ConsoleIO
import ui.controller.base.BaseUIController
import ui.utils.tryToExecute

class ViewTaskLogsUIController(
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase,
    private val consoleIO : ConsoleIO
): BaseUIController {

    override fun execute() {
        consoleIO.println("Enter Task ID :")
        val taskId = consoleIO.readFromUser()
        tryToExecute (
            action = { viewTaskLogsUseCase.viewTaskLogs(taskId) },
            onSuccess = { logs ->
                consoleIO.println("Task Logs :")
                logs.forEach { consoleIO.println(it.message) }
            }
        )
    }
}