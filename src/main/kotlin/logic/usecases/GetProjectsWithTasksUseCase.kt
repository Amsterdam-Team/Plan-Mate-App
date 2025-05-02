package logic.usecases

import logic.entities.Project
import logic.repository.ProjectRepository
import logic.repository.TaskRepository

class GetProjectsWithTasksUseCase(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): List<Project>{
        val projects = projectRepository.getProjects()
        return projects.map { project ->
            val tasks = taskRepository.getAllTasksByProjectId(project.id)
            project.copy(tasks = tasks)
        }
    }
}