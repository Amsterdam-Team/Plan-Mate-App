package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import java.util.UUID

class GetProjectsUseCase(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    fun getProject(projectID: String): Project {
        val id = validateAndParseProjectID(projectID)
        val projects = linkProjectAWithTasks()
        if (projects.isEmpty()) throw EmptyDataException
        return projects.find { it.id == id } ?: throw ProjectNotFoundException

    }

    fun getAllProjects(): List<Project>{
        return linkProjectAWithTasks()
    }

    private fun validateAndParseProjectID(projectID: String): UUID {
        return try {
            UUID.fromString(projectID)
        } catch (e: IllegalArgumentException) {
            throw InvalidProjectIDException
        }
    }
    private fun linkProjectAWithTasks():List<Project>{
        return projectRepository.getProjects().map { project ->
            val tasks = taskRepository.getAllTasksByProjectId(project.id)
            project.copy(tasks = tasks)
        }
    }
}