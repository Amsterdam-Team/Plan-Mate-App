package ui.menuHandler

import ui.controller.BaseUIController


class AdminMenuHandler(private val adminFeatureControllers: Map<Int, BaseUIController>): MainMenuHandler() {

    public override fun start() {
        super.start()
        baseMenuStart(
            showMenu = ::showAdminMenu,
            featureControllers = adminFeatureControllers
        )
    }

    private fun showAdminMenu() {
        println(
            """
    ================================
    Admin Main Menu
    ================================
    1. View All Projects
    2. Create Task
    3. Edit Task
    4. Delete Task
    5. View Tasks
    6. View Project Audit History
    7. Create Mate User
    8. Create Project
    9. Edit Project
    10. Delete Project
    11. Create State
    12. Edit State
    0. Exit
    """.trimIndent()
        )
    }




}