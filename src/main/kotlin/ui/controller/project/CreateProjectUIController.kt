package ui.controller.project

import logic.entities.Project
import logic.usecases.project.CreateProjectUseCase
import ui.controller.base.BaseUIController
import ui.utils.tryToExecute
import java.util.UUID

class CreateProjectUIController(private val createProjectUseCase: CreateProjectUseCase) :
    BaseUIController {
    override fun execute() {
        tryToExecute(
            action = ::startCreatingAProject, onSuccess = { project -> createProjectUseCase.createProject(project) })
    }

    private fun startCreatingAProject(): Project {
        println("Please enter project name")
        val projectName = readlnOrNull().toString()
        println("Enter project states (comma-separated):")
        val state = readLine() ?: ""
        val projectStates = state.split(",").map { it.trim() }

        return Project(
            id = UUID.randomUUID(), name = projectName, states = projectStates, tasks = emptyList()
        )
    }
}