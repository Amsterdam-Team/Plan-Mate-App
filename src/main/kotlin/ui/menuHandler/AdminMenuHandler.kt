package ui.menuHandler

import ui.controller.BaseUIController
import ui.utils.DisplayUtils.printBoxedMessage


class AdminMenuHandler(private val adminFeatureControllers: Map<Int, BaseUIController>) : MainMenuHandler() {

    public override suspend fun start() {
        super.start()
        baseMenuStart(
            showMenu = ::showAdminMenu, featureControllers = adminFeatureControllers
        )
    }

    private fun showAdminMenu() {
        printBoxedMessage(
            """
    ================================
    Admin Main Menu
    ================================
    1. View All Projects
    2. View State
    3. Create Task
    4. Edit Task
    5. Delete Task
    6. View Tasks
    7. View Project Audit History
    8. View Task Audit History
    9. Create Mate User
    10. Create Project
    11. Edit Project
    12. Delete Project
    13. Create State
    14. Edit State
    0. Exit
    """.trimIndent()
        )
    }

}