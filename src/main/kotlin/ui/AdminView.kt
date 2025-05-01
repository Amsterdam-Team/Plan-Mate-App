package ui

import ui.console.ConsoleIO
import ui.utils.UIController

class AdminView(val consoleIO: ConsoleIO, val handlers: Map<Int, UIController>) {

    fun handleAdminCommands() {
        while (true) {
            val input: String = consoleIO.readFromUser()

            when (val choice = input.toIntOrNull()) {
                null -> consoleIO.println("Please enter valid number")
                0 -> {
                    consoleIO.println("exiting now, goodbye")
                    return
                }

                in 1..adminCommandsMap.keys.size -> handlers[choice]?.execute()
            }
        }

    }

    fun showAdminCommands() {
        adminCommandsMap.entries.forEach {
            println("Press ${it.key} to ${it.value}")
        }
        consoleIO.println("Press 0 to exit")

    }



    private val adminCommandsMap = mapOf<Int, String>(
        1 to "Create User",
        2 to "Add Project",
        3 to "Edit Project Name",
        4 to "Edit Project States",
        5 to "Delete Project"
        // add the other rules
    )

}