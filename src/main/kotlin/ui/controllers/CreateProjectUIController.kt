package ui.controllers

import console.ConsoleIO
import logic.usecases.project.CreateProjectUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class CreateProjectUIController(
    private val createProjectUseCase: CreateProjectUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override fun execute() {

        consoleIO.println(PROJECT_NAME_PROMPT_MESSAGE)
        val projectName = consoleIO.readFromUser()
        consoleIO.println(PROJECT_STATES_PROMPT_MESSAGE)
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
            consoleIO.println(PROJECT_CREATED_SUCCESSFULLY_MESSAGE)
            return
        }
    }

    private fun onCreateProjectFail(exception: Exception) {
        consoleIO.println(FAIL_TO_CREATE_PROJECT_MESSAGE)

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
        const val FAIL_TO_CREATE_PROJECT_MESSAGE = "❌ Please Enter All Inputs Correctly, inter $RETRY or $CANCEL"
    }
}