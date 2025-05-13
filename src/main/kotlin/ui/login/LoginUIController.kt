package ui.login

import logic.entities.User
import logic.usecases.login.LoginUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSuccess
import ui.utils.DisplayUtils.printTitle
import ui.utils.DisplayUtils.promptInput
import ui.utils.tryToExecute
import kotlin.system.exitProcess

class LoginUIController(
    private val loginUseCase: LoginUseCase,
    private val adminMenuHandler: AdminMenuHandler,
    private val mateMenuHandler: MateMenuHandler,
    private val consoleIO: ConsoleIO
) : BaseUIController {
    lateinit var user: User

    override suspend fun execute() {

        printTitle(LOGIN_WELCOME_MESSAGE)
        promptInput(LOGIN_USERNAME_PROMPT_MESSAGE)
        val username = consoleIO.readFromUser()
        promptInput(LOGIN_PASSWORD_PROMPT_MESSAGE)
        val password = consoleIO.readFromUser()

        tryToExecute(
            action = {
            user = loginUseCase.validateUserCredentials(username, password)
            onLoginSuccess(user)
        }, onSuccess = { printSuccess(LOGIN_SUCCEED) }, onError = ::onLoginFailed
        )

    }

    private suspend fun onLoginSuccess(user: User) {
        if (user.isAdmin) adminMenuHandler.start() else mateMenuHandler.start()
    }

    private suspend fun onLoginFailed(exception: Exception) {
        printError(FAIL_TO_LOGIN_MESSAGE)
        val input = consoleIO.readFromUser().trim().uppercase()
        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> exitProcess(0)
            else -> onLoginFailed(exception)
        }

    }

    private companion object {
        const val LOGIN_WELCOME_MESSAGE =
            "Hello My Friend..\nI hope You Remember Your username and password to login quickly..."
        const val LOGIN_USERNAME_PROMPT_MESSAGE = "Enter Your user name : "
        const val LOGIN_PASSWORD_PROMPT_MESSAGE = "Enter Your Password : "
        const val LOGIN_SUCCEED = "U have logged in successfully"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val FAIL_TO_LOGIN_MESSAGE =
            "Please Enter All Inputs Correctly,\n Enter $RETRY to re login again or $CANCEL to exit"

    }

}