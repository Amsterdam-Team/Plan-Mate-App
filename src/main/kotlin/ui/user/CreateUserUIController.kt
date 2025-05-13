package ui.user

import logic.usecases.user.CreateUserUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.project.CreateProjectUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSubTitle
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute

class CreateUserUIController(
    private val createUserUseCase: CreateUserUseCase, private val consoleIO: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        printSubTitle(SUBTITLE_MESSAGE)

        promptInput(USERNAME_PROMPT_MESSAGE)
        val username = consoleIO.readFromUser()

        promptInput(PASSWORD_PROMPT_MESSAGE)
        val password = consoleIO.readFromUser()

        tryToExecute(
            action = { createUserUseCase.execute(username, password) }, onSuccess = { result ->
            if (result) {
                printSuccess(CREATE_MATE_USER_SUCCESSFULLY_MESSAGE)
            }
        }, onError = ::onCreateUserFailed
        )
    }

    private suspend fun onCreateUserFailed(exception: Exception) {
        printError(RETRY_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> onCreateUserFailed(exception)
        }

    }

    private companion object {
        const val SUBTITLE_MESSAGE = "Create New Mate User"
        const val USERNAME_PROMPT_MESSAGE = "Please enter Username: "
        const val PASSWORD_PROMPT_MESSAGE = "Please enter Password here: "
        const val CREATE_MATE_USER_SUCCESSFULLY_MESSAGE = "Mate user created successfully"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val RETRY_MESSAGE =
            "Please enter All Inputs Correctly,\n Enter $RETRY to re enter ur inputs again or $CANCEL to exit"

    }
}