package ui.menuHandler

import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printError
import ui.utils.DisplayUtils.printSubTitle
import ui.utils.DisplayUtils.printSuccess

open class MainMenuHandler {

    protected open suspend fun start() {
        printSubTitle(WELCOME_MESSAGE)
    }

    protected suspend fun baseMenuStart(showMenu: () -> Unit, featureControllers: Map<Int, BaseUIController>) {
        while (true) {
            showMenu()

            printSubTitle("$ENTER_NUMBER_MESSAGE ${featureControllers.size}) $TO_CHOOSE_MESSAGE")
            val input = readlnOrNull()

            when (val choice = input?.toIntOrNull()) {
                in featureControllers.keys -> featureControllers[choice]?.execute()
                0 -> {
                    printSuccess(GOOD_BY_MESSAGE)
                    break
                }

                null -> printError(INVALID_INPUT_MESSAGE)
                else -> printError(UNKNOWN_FEATURE_MESSAGE)
            }
        }
    }
    companion object{
        const val WELCOME_MESSAGE = "👋 Welcome to PlanMate!\nLet's get things organized."
        const val ENTER_NUMBER_MESSAGE = "\nPlease enter a number (1–"
        const val TO_CHOOSE_MESSAGE = "to choose a feature, or 0 to exit: "
        const val GOOD_BY_MESSAGE = "See You Later 🙂"
        const val INVALID_INPUT_MESSAGE = "Invalid input. Please enter a valid number."
        const val UNKNOWN_FEATURE_MESSAGE = "Unknown feature number."
    }
}