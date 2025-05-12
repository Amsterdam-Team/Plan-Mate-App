package logic.usecases.project

import logic.repository.ProjectRepository
import logic.repository.TaskRepository

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
    ) {
    suspend fun execute() =
        projectRepository.getProjects().map { project ->
            val tasks = taskRepository.getAllTasksByProjectId(project.id)
            project.copy(tasks = tasks)
        }
}