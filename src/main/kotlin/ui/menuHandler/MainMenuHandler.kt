package ui.menuHandler

import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSubTitle
import ui.utils.DisplayUtils.printSuccess

open class MainMenuHandler {

    protected open suspend fun start() {
        printSubTitle("👋 Welcome to PlanMate!\nLet's get things organized.")
    }

    protected suspend fun baseMenuStart(showMenu: () -> Unit, featureControllers: Map<Int, BaseUIController>) {
        while (true) {
            showMenu()

            printSubTitle("\nPlease enter a number (1–${featureControllers.size}) to choose a feature, or 0 to exit: ")
            val input = readlnOrNull()

            when (val choice = input?.toIntOrNull()) {
                in featureControllers.keys -> featureControllers[choice]?.execute()
                0 -> {
                    printSuccess("See You Later 🙂")
                    break
                }

                null -> printError("Invalid input. Please enter a valid number.")
                else -> printError("Unknown feature number.")
            }
        }
    }
}