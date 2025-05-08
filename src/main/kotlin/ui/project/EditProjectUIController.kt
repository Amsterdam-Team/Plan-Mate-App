package ui.project

import console.ConsoleIO
import logic.entities.User
import logic.usecases.project.EditProjectUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import java.util.*


class EditProjectUIController(
    private val editProjectUseCase: EditProjectUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    private val adminUser = User(
        id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        isAdmin = true,
        username ="Admin",
        password = "123456"
    )

    override suspend  fun execute() {
        consoleIO.println(EDIT_PROJECT_NAME_PROMPT_MESSAGE)

        consoleIO.println(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser().trim().let { UUID.fromString(it) }

        consoleIO.println(NEW_PROJECT_NAME_PROMPT_MESSAGE)
        val newName = consoleIO.readFromUser().trim()


        tryToExecute(
            action = {
                editProjectUseCase.editProjectName(
                    projectId = projectId,
                    newName = newName)
            },
            onSuccess = ::onEditProjectSuccess,
            onError = ::onEditProjectFail
        )
    }

    private fun onEditProjectSuccess(isUpdatedSuccessfully: Boolean) {
        if (isUpdatedSuccessfully) {
            consoleIO.println(PROJECT_UPDATED_SUCCESSFULLY_MESSAGE)
            return
        }
    }

    private suspend  fun onEditProjectFail(exception: Exception) {
        consoleIO.println(FAIL_TO_UPDATE_PROJECT_NAME_MESSAGE)
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
            "❌ Please Inter All Inputs Correctly, inter $RETRY or $CANCEL"
    }
}


