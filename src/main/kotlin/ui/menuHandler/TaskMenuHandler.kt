package ui.menuHandler

import ui.controller.BaseUIController

class TaskMenuHandler(
    private val taskFeatureControllers: Map<Int, BaseUIController>
) : MainMenuHandler() {

    public override suspend fun start() {
        super.start()
        baseMenuStart(
            showMenu = ::showTaskMenu,
            featureControllers = taskFeatureControllers
        )
    }

    private fun showTaskMenu() {
        println(
            """
            ========================
            📋 Task Menu
            ========================
            [1] Create task New          [2] View task details          [0] Quit   
         
            """.trimIndent()
        )
    }
}
