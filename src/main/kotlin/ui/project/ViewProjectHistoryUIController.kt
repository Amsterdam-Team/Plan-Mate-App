package ui.project

import console.ConsoleIO
import logic.usecases.project.ViewProjectHistoryUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.formatLogItem

class ViewProjectHistoryUIController(
    private val viewProjectHistoryUseCase: ViewProjectHistoryUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override fun execute() {
        tryToExecute(
            action = {
                consoleIO.println("Please Enter Project ID:")
                viewProjectHistoryUseCase.execute(consoleIO.readFromUser())
            },
            onSuccess = {
                it.forEach { consoleIO.println(formatLogItem(it)) }
            }
        )
    }

}