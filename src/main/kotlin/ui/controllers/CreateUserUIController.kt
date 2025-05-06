package ui.controllers

import logic.usecases.user.CreateUserUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class CreateUserUIController(
    private val createUserUseCase: CreateUserUseCase,
    private val io: ConsoleIO
) : BaseUIController {

    override fun execute() {
        io.println("Create New Mate User")

        io.println("Enter Username: ")
        val username = io.readFromUser()

        io.println("Enter Password: ")
        val password = io.readFromUser()

        tryToExecute(
            action = { createUserUseCase.execute(username, password) },
            onSuccess = { result ->
                if(result){
                    "Mate user created successfully"
                }else{
                    "Failed creating user"
                }
            }
        )
    }
}