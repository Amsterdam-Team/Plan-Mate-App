package ui.task

import logic.usecases.task.EditTaskUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class EditTaskUiController(val editTaskUseCase: EditTaskUseCase, val consoleIO: ConsoleIO) : BaseUIController {
    override suspend fun execute() {


        val taskId = getTaskId()
        val newName = getNewTaskName()
        val newState = getNewTaskState()
        tryToExecute<Boolean>(
            action = { editTaskUseCase.editTask(taskId, newName, newState) },
            onSuccess = { onSuccess(it) }
        )


    }


    private fun getTaskId(): String {
        consoleIO.println("please enter task id here: ")
        while (true) {
            val taskId = consoleIO.readFromUser()

            if (taskId.isNotBlank() && taskId.isNotEmpty()) {
                return taskId
            }
        }
    }

    private fun getNewTaskName(): String {
        consoleIO.println("please enter new name here: ")
        while (true) {
            val name = consoleIO.readFromUser()
            if (name.isNotBlank() && name.isNotEmpty()) {
                return name
            }
        }
    }

    private fun getNewTaskState(): String {
        consoleIO.println("please enter new state here: ")
        while (true) {
            val state = consoleIO.readFromUser()
            if (state.isNotBlank() && state.isNotEmpty()) {
                return state
            }
        }
    }

    private fun onSuccess(result: Boolean) {
        if (result) {
            consoleIO.println("Task updated successfully")

        } else {
            consoleIO.println("Failed updating task")
        }
    }


}