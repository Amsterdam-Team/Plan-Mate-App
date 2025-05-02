package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.*

class DeleteStateUseCase(private val repository: ProjectRepository) {
    operator fun invoke(stateId: UUID, projectId: UUID): Boolean {
//        throw  ProjectNotFoundException
        return repository.deleteStateById(stateId, projectId)

    }
}

