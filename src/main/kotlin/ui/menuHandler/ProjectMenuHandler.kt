package ui.menuHandler

import logic.entities.User
import ui.controller.BaseUIController

class ProjectMenuHandler(private val projectControllers: Map<Int, BaseUIController>, private val user: User) : MainMenuHandler() {

    public override suspend fun start() {
        super.start()
        baseMenuStart(
            showMenu = ::showProjectMenu,
            featureControllers = projectControllers
        )
    }

    private fun showProjectMenu() {
        if (user.isAdmin){
            println(
                """
            1. Create Project          2. View Project          3. Edit Project          4. Delete Project
            5. projects logs           6. tasks logs            0. Back
            """.trimIndent()
            )
        }else {
            println(
                """
            1. View Project            2. projects logs         3. tasks logs          0. Back
            """.trimIndent()
            )
        }

    }
}
