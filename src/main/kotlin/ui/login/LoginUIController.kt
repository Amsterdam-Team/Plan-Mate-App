package ui.login

import logic.entities.User
import logic.usecases.login.LoginUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.utils.tryToExecute

class LoginUIController(
    private val loginUseCase: LoginUseCase,
    private val adminMenuHandler: AdminMenuHandler,
    private val mateMenuHandler: MateMenuHandler,
    private val consoleIO: ConsoleIO
) : BaseUIController {
    lateinit var user: User

    override suspend fun execute() {
        consoleIO.println("Hello My Friend..\nI hope You Remember Your username and password to login quickly...\nEnter Your user name : ")
        val username = consoleIO.readFromUser()
        consoleIO.println("Enter Your Password : ")
        val password = consoleIO.readFromUser()
        tryToExecute(
            action = {
                user = loginUseCase.validateUserCredentials(username, password)
                onLoginSuccess(user)
            },
            onSuccess = { consoleIO.println("Success Login......") },
            onError = ::onLoginUIFail
        )

    }


    private suspend fun onLoginSuccess(user: User) {
        if (user.isAdmin) adminMenuHandler.start() else mateMenuHandler.start()
    }

    private suspend fun onLoginUIFail(exception: Exception) {
        consoleIO.println(FAIL_TO_LOGIN_MESSAGE)
        consoleIO.println("🔁 Type '$RETRY' to try again or '$CANCEL' to cancel:")
        val input = consoleIO.readFromUser().trim().uppercase()

        when (input) {
            RETRY.uppercase() -> execute()
            CANCEL.uppercase() -> return
            else -> {
                consoleIO.println("❗ Invalid input. Please type '$RETRY' or '$CANCEL'.")
                onLoginUIFail(exception)
            }
        }
    }

    private companion object {
        const val RETRY = "retry"
        const val CANCEL = "cancel"
        const val FAIL_TO_LOGIN_MESSAGE = "❌ Invalid username or password."
    }
}