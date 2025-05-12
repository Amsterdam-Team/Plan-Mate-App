package logic.usecases.state

import logic.repository.IProjectRepository
import java.util.*

class DeleteStateUseCase(private val repository: IProjectRepository) {
    suspend operator fun invoke(projectId: UUID, oldState: String): Boolean {
//        throw  ProjectNotFoundException
        return repository.deleteStateById(projectId, oldState)

    }
}

