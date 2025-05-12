package logic.usecases.project

import logic.repository.IProjectRepository
import logic.usecases.task.GetAllTasksByProjectIdUseCase

class GetAllProjectsUseCase(
    private val projectRepository: IProjectRepository,
    private val getTasksUseCase: GetAllTasksByProjectIdUseCase
) {
    suspend fun execute() =
        projectRepository.getProjects().map { project ->
            val tasks = getTasksUseCase(project.id.toString())
            project.copy(tasks = tasks)
        }
}