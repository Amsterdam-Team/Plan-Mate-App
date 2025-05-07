package logic.usecases.project

import logic.repository.ProjectRepository

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute() =
        projectRepository.getProjects()
}