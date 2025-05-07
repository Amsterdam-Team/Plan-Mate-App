package ui.controllers

import logic.usecases.state.AddStateUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.printAsASuccessState
import ui.utils.tryToExecute
import java.util.UUID

class AddStateUIController(
    private val useCase: AddStateUseCase,
    private val io: ConsoleIO
) : BaseUIController {

    override suspend fun execute() {
        io.println("➕ Add New State to the Project")

        io.println("Enter Project ID: ")
        val projectId = io.readFromUser()

        io.println("Enter State Name: ")
        val state = io.readFromUser()

        tryToExecute(
            action = { useCase.execute(projectId, state) },
            onSuccess = {
                result -> if(result){
                    println("A new state added successfully")
            }else{
                println("Failed adding new state")
            }
            }
        )
    }
}