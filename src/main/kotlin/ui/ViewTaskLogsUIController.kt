package ui

import logic.usecases.ViewTaskLogsUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import java.util.UUID

class ViewTaskLogsUIController(
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase
): BaseUIController {

    override fun execute() {
        println("Enter Task ID :")
        val taskId = readLine().toString()
        tryToExecute (
            action = { viewTaskLogsUseCase.viewTaskLogs(taskId) },
            onSuccess = { logs ->
                println("Task Logs :")
                logs.forEach { println(it.message) }
            }
        )
    }
}