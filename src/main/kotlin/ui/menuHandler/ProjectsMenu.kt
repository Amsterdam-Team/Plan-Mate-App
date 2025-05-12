package ui.menuHandler

import logic.entities.User
import ui.project.CreateProjectUIController
import ui.project.DeleteProjectUiController
import ui.project.EditProjectUIController
import ui.project.ViewAllProjectsUIController

class ProjectsMenu(
    val user: User,
    val viewAllProjectsUIController: ViewAllProjectsUIController,
    val createProjectUIController: CreateProjectUIController,
    val deleteProjectUiController: DeleteProjectUiController,
    val editProjectUIController: EditProjectUIController
) {


    private fun showMenu(){
        if (user.isAdmin){

        }
    }
}