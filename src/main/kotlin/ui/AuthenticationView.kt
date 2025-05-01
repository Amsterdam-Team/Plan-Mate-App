package ui

import logic.entities.User
import ui.console.ConsoleIO
import ui.utils.AuthUIController

class AuthenticationView (val consoleIO: ConsoleIO,val loginUIController: AuthUIController, val createUserUIController: AuthUIController ) {

    fun handleAuthentication() : User? {
        while (true){
            val input = consoleIO.readFromUser().toIntOrNull()
            when (input){
                0 -> {
                    consoleIO.println("exiting now, goodbye")
                    return null
                }
                1 ->  {
                    return loginUIController.execute()
                }
                2 -> {
                    return createUserUIController.execute()
                }
                null -> consoleIO.println("Please Enter Valid Number")
                else -> consoleIO.println("Please Enter Valid Number")

            }
        }


    }
    fun showAuthCommands(){
        consoleIO.println("Hello :) ")
        consoleIO.println("Press 1 to login")
        consoleIO.println("Press 2 to create a new account")
        consoleIO.println("Press 0 to exit")
    }


}