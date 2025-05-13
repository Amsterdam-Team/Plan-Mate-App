package ui.login

import logic.entities.User
import logic.usecases.login.LoginUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.menuHandler.ProjectsView
import ui.utils.tryToExecute

class LoginUIController(
    private val loginUseCase: LoginUseCase,
    private val adminMenuHandler: AdminMenuHandler,
    private val mateMenuHandler: MateMenuHandler,
    private val consoleIO: ConsoleIO,
    private val projectsView: ProjectsView
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
            },
            onSuccess = {
                consoleIO.println("Success Login......")
                projectsView.start()
            },
            onError = ::onError
        )

    }

    suspend fun onError(exception: Exception) {
        consoleIO.println("Please enter [retry] to login again or [cancel] to end the process")
        while (true){
            val input = consoleIO.readFromUser()
            if (input == "retry" ){
                execute()
            }else if ( input == "cancel"){
                consoleIO.println("Program exit.")
                return
            }else {
                consoleIO.println("Please enter either [retry] or [cancel] commands")
            }
        }
    }
}