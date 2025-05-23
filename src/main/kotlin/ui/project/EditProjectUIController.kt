package ui.project

import logic.usecases.project.EditProjectUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSubTitle
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute
import java.util.*


class EditProjectUIController(
    private val editProjectUseCase: EditProjectUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        printSubTitle(EDIT_PROJECT_NAME_PROMPT_MESSAGE)

        promptInput(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser().trim().let { UUID.fromString(it) }

        promptInput(NEW_PROJECT_NAME_PROMPT_MESSAGE)
        val newName = consoleIO.readFromUser().trim()


        tryToExecute(
            action = {
                editProjectUseCase.editProjectName(
                    projectId = projectId, newName = newName
                )
            }, onSuccess = ::onEditProjectSuccess, onError = ::onEditProjectFail
        )
    }

    private fun onEditProjectSuccess(isUpdatedSuccessfully: Boolean) {
        if (isUpdatedSuccessfully) {
            printSuccess(PROJECT_UPDATED_SUCCESSFULLY_MESSAGE)
            return
        }
    }

    private suspend fun onEditProjectFail(exception: Exception) {
        printError(FAIL_TO_UPDATE_PROJECT_NAME_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()

        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onEditProjectFail(exception)
        }
    }

    private companion object {
        const val EDIT_PROJECT_NAME_PROMPT_MESSAGE = "Edit Project Name"
        const val PROJECT_ID_PROMPT_MESSAGE = "Enter project ID: "
        const val NEW_PROJECT_NAME_PROMPT_MESSAGE = "Enter new project name: "
        const val PROJECT_UPDATED_SUCCESSFULLY_MESSAGE = "✅ Project name updated successfully!"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val FAIL_TO_UPDATE_PROJECT_NAME_MESSAGE =
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"
    }
}


