package ui

import ui.console.ConsoleIO
import ui.utils.UIController

class UserView(val consoleIO: ConsoleIO, val handlers: Map<Int, UIController>) {
    fun showUserCommands() {
        userCommandsMap.entries.forEach {
            println("Press ${it.key} to ${it.value}")
        }
        consoleIO.println("Press 0 to exit")

    }

    fun handleUserCommands() {
        while (true) {
            val input: String = consoleIO.readFromUser()
            when (val choice = input.toIntOrNull()) {
                null -> consoleIO.println("Please enter valid number")
                0 -> {
                    consoleIO.println("exiting now, goodbye")
                    return
                }

                in 1..userCommandsMap.keys.size -> handlers[choice]?.execute()
            }

        }
    }

    private val userCommandsMap = mapOf<Int, String>(
        1 to "Add Task",
        2 to "Edit Task Name",
        3 to "Edit Task State",
        4 to "Delete Task",

        )
}