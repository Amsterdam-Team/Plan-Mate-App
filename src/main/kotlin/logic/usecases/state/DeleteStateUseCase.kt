package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.*

class DeleteStateUseCase(private val repository: ProjectRepository) {
    operator fun invoke(projectId: UUID, oldState: String) {
//        throw  ProjectNotFoundException
        return repository.deleteStateById(projectId, oldState)

    }
}

