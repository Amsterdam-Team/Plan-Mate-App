package ui.menuHandler

import ui.controller.BaseUIController

class ProjectMenuHandler(private val projectControllers: Map<Int, BaseUIController>) : MainMenuHandler() {

    public override suspend fun start() {
        super.start()
        baseMenuStart(
            showMenu = ::showProjectMenu,
            featureControllers = projectControllers
        )
    }

    private fun showProjectMenu() {
        println(
            """
            1. Create Project          2. View Project          3. Edit Project          4. Delete Project          0. Back
            """.trimIndent()
        )
    }
}
