package ui.menuHandler

import ui.controller.BaseUIController

class MateMenuHandler(private val mateFeatureControllers: Map<Int, BaseUIController>): MainMenuHandler(){

    public override fun start() {
        baseMenuStart(
            showMenu = ::showMateMenu,
            featureControllers = mateFeatureControllers
        )
    }

    private fun showMateMenu() {
        println(
            """
    ================================
    Mate Main Menu
    ================================
    1. View All Projects
    2. Create Task
    3. Edit Task
    4. Delete Task
    5. View Tasks
    6. View Project Audit History
    0. Exit
    """.trimIndent()
        )

    }
}