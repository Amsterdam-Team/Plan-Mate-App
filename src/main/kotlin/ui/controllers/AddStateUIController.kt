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

    override fun execute() {
        io.println("➕ Add New State to the Project")

        io.println("Enter Project ID: ")
        val projectId = runCatching { UUID.fromString(io.readFromUser()) }.getOrNull()
        if (projectId == null) {
            io.println("Invalid UUID format")
            return
        }

        io.println("Enter State Name: ")
        val state = io.readFromUser()

        tryToExecute(
            action = { useCase.execute(projectId, state) },
            onSuccess = {
                "State added successfully".printAsASuccessState()
            }
        )
    }
}