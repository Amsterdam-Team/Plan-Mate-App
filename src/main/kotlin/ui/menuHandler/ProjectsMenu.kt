package ui.menuHandler

import logic.entities.Project
import logic.entities.User
import logic.usecases.project.GetAllProjectsUseCase
import logic.usecases.utils.StateManager
import ui.console.ConsoleIO
import ui.project.CreateProjectUIController
import ui.project.DeleteProjectUiController
import ui.project.EditProjectUIController
import ui.project.ViewAllProjectsUIController
import ui.utils.printSwimlanesView

class ProjectsView(
    val stateManager: StateManager,
    val getAllProjectsUseCase: GetAllProjectsUseCase,
    val consoleIO: ConsoleIO
) {
    lateinit var allProjects :List<Project>
     suspend fun start(){
        allProjects = getAllProjectsUseCase.execute()
         showCurrentProject()
         showMenu()
         handleMenuCommand()
    }

    private fun showCurrentProject(){
        if(allProjects.isEmpty()){
            consoleIO.println("No available projects")
            return
        }
        allProjects.forEachIndexed { index, project ->
            consoleIO.println("${index+1}) ${project.name}")
        }
    }
    private fun showMenu(){

        if (stateManager.getLoggedInUser().isAdmin){
            consoleIO.println("""
                Enter specific command:
                [1] view specific project
                [2] create user
            """.trimIndent())
        }else {
            consoleIO.println("""
                Enter specific command:
                view specific project; please enter specific project number
                
            """.trimIndent())
        }
    }
    private fun handleMenuCommand(){
        if (stateManager.getLoggedInUser().isAdmin){

            handleAdminMenuCommand()
        }else{
            handleMateMenuCommand()
        }


    }
    private fun handleMateMenuCommand(){
        val projectNumber = getProjectNumber()
        printSwimlanesView(allProjects[projectNumber-1])
        // show menu with command for project depend on
    }
    private fun handleAdminMenuCommand(){
        while (true){
            val command = consoleIO.readFromUser()
            when (command.toIntOrNull()){
                0 -> return
                1 -> showSpecificProject()
                else -> "Please enter valid command"
            }
        }
    }

    private fun getProjectNumber():Int{
        while (true){
            val command = consoleIO.readFromUser().toIntOrNull()
            when(command){
                null -> "Please enter valid project number"
                in 1..allProjects.size -> return command
            }
        }
    }
    private fun showSpecificProject(){
        val projectNumber = getProjectNumber()
        printSwimlanesView(allProjects[projectNumber-1])
    }
}