package ui.project

import logic.usecases.project.CreateProjectUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class CreateProjectUIController(
    private val createProjectUseCase: CreateProjectUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {

        promptInput(PROJECT_NAME_PROMPT_MESSAGE)
        val projectName = consoleIO.readFromUser()
        promptInput(PROJECT_STATES_PROMPT_MESSAGE)
        val state = consoleIO.readFromUser()
        val projectStates = state.split(",").map { it.trim() }

        tryToExecute(
            action = { createProjectUseCase.createProject(name = projectName, states = projectStates) },
            onSuccess = ::onCreateProjectSuccess,
            onError = ::onCreateProjectFail
        )
    }

    private fun onCreateProjectSuccess(isCreatedSuccessfully: Boolean) {
        if (isCreatedSuccessfully) {
            printSuccess(PROJECT_CREATED_SUCCESSFULLY_MESSAGE)
            return
        }
    }

    private suspend fun onCreateProjectFail(exception: Exception) {
        printError(FAIL_TO_CREATE_PROJECT_MESSAGE)

        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onCreateProjectFail(exception)
        }
    }


    private companion object {
        const val PROJECT_NAME_PROMPT_MESSAGE = "Please enter project name"
        const val PROJECT_STATES_PROMPT_MESSAGE = "Enter project states (comma-separated):"
        const val PROJECT_CREATED_SUCCESSFULLY_MESSAGE = "✅ Your project has been Created Successfully"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val FAIL_TO_CREATE_PROJECT_MESSAGE =
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"
    }
}