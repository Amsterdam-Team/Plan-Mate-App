package ui

import logic.usecases.project.DeleteProjectUseCase
import ui.console.ConsoleIO

class DeleteProjectUiController(val deleteProjectUseCase: DeleteProjectUseCase, val consoleIO: ConsoleIO) {

    fun execute() {}
}