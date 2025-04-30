package ui.utils

import ui.controller.BaseUIController

fun baseMenuStart(showMenu: () -> Unit, featureControllers: Map<Int, BaseUIController>){
        while (true) {
            showMenu()

            print("\nPlease enter a number (1–${featureControllers.size}) to choose a feature, or 0 to exit: ")
            val input = readlnOrNull()

            when (val choice = input?.toIntOrNull()) {
                in featureControllers.keys -> featureControllers[choice]?.execute()
                0 -> {
                    "See You Later 🙂".printAsASuccessState()
                    break
                }
                null -> "❌ Invalid input. Please enter a valid number.".printAsAFailState()
                else -> "❌ Unknown feature number.".printAsAFailState()
            }
        }
    }