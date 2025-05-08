package ui.project

import console.ConsoleIO
import logic.usecases.project.GetProjectHistoryUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.formatLogItem

class ViewProjectHistoryUIController(
    private val getProjectHistoryUseCase: GetProjectHistoryUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        tryToExecute(
            action = {
                consoleIO.println("Please Enter Project ID:")
                getProjectHistoryUseCase.execute(consoleIO.readFromUser())
            },
            onSuccess = {
                it.forEach { consoleIO.println(formatLogItem(it)) }
            }
        )
    }

}