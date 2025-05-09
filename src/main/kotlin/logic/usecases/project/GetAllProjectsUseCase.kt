package logic.usecases.project

import logic.repository.ProjectRepository
import logic.usecases.task.GetAllTasksByProjectIdUseCase

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository,
    private val getTasksUseCase: GetAllTasksByProjectIdUseCase
) {
    suspend fun execute() =
        projectRepository.getProjects().map { project ->
            val tasks = getTasksUseCase(project.id.toString())
            project.copy(tasks = tasks)
        }
}