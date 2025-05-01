package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.UUID

class GetProjectStatesUseCase(private val repository: ProjectRepository) {

    fun execute(projectID: UUID): List<String> {

        return repository.getProject(projectID).states
    }
}