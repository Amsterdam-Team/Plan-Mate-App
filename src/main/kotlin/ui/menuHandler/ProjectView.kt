package ui.menuHandler

import TaskManagerView
import logic.entities.Project
import logic.entities.Task
import logic.usecases.logs.ViewTaskLogsUseCase
import logic.usecases.project.CreateProjectUseCase
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.project.GetAllProjectsUseCase
import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.DeleteTaskUseCase
import logic.usecases.user.CreateUserUseCase
import logic.usecases.utils.StateManager
import ui.console.ConsoleIO
import ui.utils.getErrorMessageByException
import ui.utils.printSwimlanesView
import ui.utils.tryToExecute

class ProjectsView(
    private val stateManager: StateManager,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val consoleIO: ConsoleIO,
    private val taskManagerView: TaskManagerView,
    private val createUserUseCase: CreateUserUseCase,
    private val createProjectsUseCase: CreateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase

) {
    private lateinit var allProjects: List<Project>
    private lateinit var currentProject: Project
    private lateinit var currentProjectTasks: List<Task>
    suspend fun start() {
        loadProjects()
        showProjectsList()
        handleMenuOptions()
    }

    private suspend fun loadProjects() {
        allProjects = getAllProjectsUseCase.execute()
    }

    private fun showProjectsList() {
        consoleIO.println("Available Projects:")
        allProjects.forEachIndexed { index, project ->
            consoleIO.println("${index + 1}) ${project.name}")
        }
    }

    suspend fun handleMenuOptions() {
        consoleIO.println("Enter specific command")
        if (stateManager.getLoggedInUser().isAdmin) {
            handleAdminOptions()
        } else {
            handleProjectSelection()
        }

    }

    suspend fun handleAdminOptions() {
        try {
            consoleIO.println(
                """
            [1] Enter the project number to view
            [2] create user
            [3] Create a New Project
            [4] Show Project Logs
            [5] Delete Project
            [6] Edit project
            [7] Add state
            [0] Exit
        """.trimIndent()
            )
            val input = getValidatedInputString()
            when (input) {
                "1" -> handleProjectSelection()
                "2" -> createUser()
                "3" -> createProject()
                "4" -> Unit
                "5" -> deleteProject()
                "6" -> Unit
                "7" -> Unit
                "0" -> return
            }

        } catch (e: Exception) {
            getErrorMessageByException(e)
            return
        }
    }

    private suspend fun handleProjectSelection() {
        consoleIO.println("Enter the project number to view, or 0 to exit:")
        val input = consoleIO.readFromUser().trim()
        if (input == "0") return

        val projectIndex = input.toIntOrNull()
        if (projectIndex == null || projectIndex !in 1..allProjects.size) {
            consoleIO.println("Invalid project number. Please try again.")
            return handleProjectSelection()
        }

        currentProject = allProjects[projectIndex - 1]
        currentProjectTasks = currentProject.tasks
        showProjectDetails()
        start()
    }

    private suspend fun showProjectDetails() {
        val project = currentProject
        consoleIO.println("Viewing Project: ${project.name}")
        printSwimlanesView(project)
        taskManagerView.showTaskOptions(project)
    }


     private suspend fun createUser() {
        consoleIO.println("Enter username")
        val username = getValidatedInputString()
        consoleIO.println("Enter password")
        val password = getValidatedInputString()
        if (createUserUseCase.execute(username, password)) {
            consoleIO.println("User created successfully")
            start()
        }
    }

    suspend fun createProject() {
        consoleIO.println("Enter project name:")
        val projectName = getValidatedInputString()
        consoleIO.println("Enter project states (comma separated; ex. todo, in progress....):")
        val projectStateString = getValidatedInputString()
        if (projectName.isEmpty() || projectStateString.isEmpty()) {
            createProject()
        }
        if (createProjectsUseCase.createProject(projectName, projectStateString.split(","))) {
            consoleIO.println("Project with name ${projectName} created successfully")
            start()
        }
    }

    suspend fun deleteProject() {
        consoleIO.println("Enter the project number to view, or 0 to exit:")
        val input = consoleIO.readFromUser().trim()
        if (input == "0") return

        val projectIndex = input.toIntOrNull()
        if (projectIndex == null || projectIndex !in 1..allProjects.size) {
            consoleIO.println("Invalid project number. Please try again.")
            deleteProject()
        }
        val currentSSelectedProject = projectIndex?.let { allProjects.get(it) }
        if (deleteProjectUseCase.deleteProject(currentSSelectedProject?.id.toString())) {
            consoleIO.println("Project deleted successfully")
            start()
        }

    }

    private fun getValidatedInputString(): String {

        val input = consoleIO.readFromUser().trim()
        if (input.isNotEmpty()) {
            return input
        } else {
            return getValidatedInputString()
        }
    }
}
