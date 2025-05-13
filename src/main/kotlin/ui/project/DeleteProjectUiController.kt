package ui.project

import logic.usecases.project.DeleteProjectUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class DeleteProjectUiController(val deleteProjectUseCase: DeleteProjectUseCase, val consoleIO: ConsoleIO) :
    BaseUIController {
    override suspend fun execute() {
        val projectId = getProjectId()
        tryToExecute(
            action = { deleteProjectUseCase.deleteProject(projectId = projectId) },
            onSuccess = { onSuccess(it) },
            onError = ::onDeleteProjectFailed
        )
    }


    private fun getProjectId(): String {
        promptInput(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser()
        while (true) {
            if (projectId.isNotBlank() && projectId.isNotEmpty()) {
                return projectId
            }
        }
    }

    private fun onSuccess(result: Boolean) {
        if (result) {
            printSuccess(SUCCESS_DELETE_PROJECT_MESSAGE)

        } else {
            printError(FAILED_DELETE_PROJECT_MESSAGE)
        }
    }

    private suspend fun onDeleteProjectFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onDeleteProjectFailed(exception)
        }

    }

    private companion object {
        const val PROJECT_ID_PROMPT_MESSAGE = "Please enter project id here: "
        const val SUCCESS_DELETE_PROJECT_MESSAGE = "Project deleted successfully"
        const val FAILED_DELETE_PROJECT_MESSAGE = "Failed deleting project"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE = "Enter $RETRY to re enter a new id again or $CANCEL to exit"

    }


}