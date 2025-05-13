package ui.task

import logic.usecases.task.EditTaskUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class EditTaskUiController(val editTaskUseCase: EditTaskUseCase, val consoleIO: ConsoleIO) : BaseUIController {
    override suspend fun execute() {


        val taskId = getTaskId()
        val newName = getNewTaskName()
        val newState = getNewTaskState()
        tryToExecute(
            action = { editTaskUseCase.editTask(taskId, newName, newState) },
            onSuccess = { onSuccess(it) },
            onError = ::onEditTaskFailed
        )


    }


    private fun getTaskId(): String {
        promptInput(TASK_ID_PROMPT_MESSAGE)
        while (true) {
            val taskId = consoleIO.readFromUser()

            if (taskId.isNotBlank() && taskId.isNotEmpty()) {
                return taskId
            }
        }
    }

    private fun getNewTaskName(): String {
        promptInput(NEW_TASK_NAME_PROMPT_MESSAGE)
        while (true) {
            val name = consoleIO.readFromUser()
            if (name.isNotBlank() && name.isNotEmpty()) {
                return name
            }
        }
    }

    private fun getNewTaskState(): String {
        promptInput(NEW_STATE_NAME_PROMPT_MESSAGE)
        while (true) {
            val state = consoleIO.readFromUser()
            if (state.isNotBlank() && state.isNotEmpty()) {
                return state
            }
        }
    }

    private fun onSuccess(result: Boolean) {
        if (result) {
            printSuccess(TASK_UPDATED_SUCCESSFULLY_MESSAGE)
        }
    }

    private suspend fun onEditTaskFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onEditTaskFailed(exception)
        }

    }

    private companion object {
        const val TASK_ID_PROMPT_MESSAGE = "Please enter task id here :"
        const val NEW_TASK_NAME_PROMPT_MESSAGE = "please enter new task name here: \""
        const val NEW_STATE_NAME_PROMPT_MESSAGE = "Please enter new state name here: "
        const val TASK_UPDATED_SUCCESSFULLY_MESSAGE = "Task updated successfully"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE =
            "Failed updating ur task\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"

    }


}