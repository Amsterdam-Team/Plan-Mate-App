package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.UUID

class GetProjectStatesUseCase(private val repository: ProjectRepository) {

    fun execute(projectID: String): List<String> {

        return repository.getProject(UUID.fromString(projectID)).states
    }
}