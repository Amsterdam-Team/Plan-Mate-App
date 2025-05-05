package ui.controller

import console.ConsoleIO
import logic.usecases.task.CreateTaskUseCase
import ui.utils.tryToExecute

class CreateTaskUIController(
    private val createTaskUseCase: CreateTaskUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override fun execute() {
        consoleIO.println(TASK_NAME_PROMPT_MESSAGE)
        val taskName = consoleIO.readFromUser()

        consoleIO.println(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser()

        consoleIO.println(TASK_STATE_PROMPT_MESSAGE)
        val taskState = consoleIO.readFromUser()

        tryToExecute(
            action = {
                createTaskUseCase.createTask(
                    name = taskName,
                    projectId = projectId,
                    state = taskState
                )
            },
            onSuccess = ::onCreateTaskSuccess,
            onError = ::onCreateTaskFail
        )
    }

    private fun onCreateTaskSuccess(isCreatedSuccessfully: Boolean) {
        if (isCreatedSuccessfully) {
            consoleIO.println(TASK_CREATED_SUCCESSFULLY_MESSAGE)
            return
        }
    }

    private fun onCreateTaskFail(exception: Exception) {
        consoleIO.println(FAIL_TO_CREATE_TASK_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()

        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onCreateTaskFail(exception)
        }

    }

    private companion object {
        const val TASK_NAME_PROMPT_MESSAGE = "Please enter task name"
        const val PROJECT_ID_PROMPT_MESSAGE = "Please enter project ID"
        const val TASK_STATE_PROMPT_MESSAGE = "Please enter task state"
        const val TASK_CREATED_SUCCESSFULLY_MESSAGE = "✅ Your Task Created Successfully"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val FAIL_TO_CREATE_TASK_MESSAGE =
            "❌ Please Inter All Inputs Correctly, inter $RETRY or $CANCEL"
    }
}