package ui.task

import logic.usecases.task.CreateTaskUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class CreateTaskUIController(
    private val createTaskUseCase: CreateTaskUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        promptInput(TASK_NAME_PROMPT_MESSAGE)
        val taskName = consoleIO.readFromUser()

        promptInput(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser()

        promptInput(TASK_STATE_PROMPT_MESSAGE)
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
            printSuccess(TASK_CREATED_SUCCESSFULLY_MESSAGE)
            return
        }
    }

    private suspend fun onCreateTaskFail(exception: Exception) {
        printError(FAIL_TO_CREATE_TASK_MESSAGE)
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
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"
    }
}