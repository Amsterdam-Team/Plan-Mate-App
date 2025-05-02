package ui

import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.task.EditTaskUseCase
import ui.console.ConsoleIO

class EditTaskUiController(val editTaskUseCase: EditTaskUseCase, val consoleIO: ConsoleIO) {

    fun execute() {}
}