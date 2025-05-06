package ui.controller.project

import logic.usecases.project.GetProjectHistoryUseCase
import ui.console.ConsoleIO
import ui.controller.base.BaseUIController
import ui.utils.tryToExecute
import ui.utils.formatLogItem

class ViewProjectHistoryUIController(
    private val getProjectHistoryUseCase: GetProjectHistoryUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override fun execute() {
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