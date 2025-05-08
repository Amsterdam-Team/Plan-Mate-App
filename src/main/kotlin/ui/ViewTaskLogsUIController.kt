package ui

import logic.usecases.ViewTaskLogsUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class ViewTaskLogsUIController(
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase,
    private val consoleIO : ConsoleIO
): BaseUIController {

    override suspend fun execute() {
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