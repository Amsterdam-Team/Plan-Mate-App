package ui

import logic.entities.User
import logic.usecases.LoginUseCase
import ui.controller.BaseUIController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.utils.printAsASuccessState
import ui.utils.tryToExecute

class LoginUIController(
    private val loginUseCase: LoginUseCase,
    private val adminMenuHandler: AdminMenuHandler,
    private val mateMenuHandler: MateMenuHandler
): BaseUIController {

    override fun execute() {
        println("Hello My Friend..\nI hope You Remember Your username and password to login quickly...\nEnter Your user name : ")
        val username = readLine().toString()
        println("Enter Your Password : ")
        val password = readLine().toString()
        tryToExecute (
            action = {
                val user = loginUseCase.validateUserCredentials(username,password)
                onLoginSuccess(user)
            },
            onSuccess = { println("Success Login......".printAsASuccessState()) }
        )

    }
    private fun onLoginSuccess(user: User) {
        if (user.isAdmin) adminMenuHandler.start() else mateMenuHandler.start()
    }
}