package ui

import logic.entities.User
import logic.usecases.LoginUseCase
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
): BaseUIController {
    lateinit var user : User

    override suspend  fun execute() {
        consoleIO.println("Hello My Friend..\nI hope You Remember Your username and password to login quickly...\nEnter Your user name : ")
        val username = consoleIO.readFromUser()
        consoleIO.println("Enter Your Password : ")
        val password = consoleIO.readFromUser()
        tryToExecute (
            action = {
                user = loginUseCase.validateUserCredentials(username,password)
                onLoginSuccess(user)
            },
            onSuccess = { consoleIO.println("Success Login......") }
        )

    }
    private suspend fun onLoginSuccess(user: User) {
        if (user.isAdmin) adminMenuHandler.start() else mateMenuHandler.start()
    }

    private fun getCurrentUser() : User = user
}