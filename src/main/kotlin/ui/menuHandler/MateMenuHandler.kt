package ui.menuHandler

import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printBoxedMessage

class MateMenuHandler(private val mateFeatureControllers: Map<Int, BaseUIController>) : MainMenuHandler() {

    public override suspend fun start() {
        baseMenuStart(
            showMenu = ::showMateMenu, featureControllers = mateFeatureControllers
        )
    }

    private fun showMateMenu() {
        printBoxedMessage(
            """
    ================================
    Mate Main Menu
    ================================
    1. View All Projects
    2. View State
    3. Create Task
    4. Edit Task
    5. Delete Task
    6. View Tasks
    7. View Project Audit History
    8. View Task Audit History
    0. Exit
    """.trimIndent()
        )

    }
}