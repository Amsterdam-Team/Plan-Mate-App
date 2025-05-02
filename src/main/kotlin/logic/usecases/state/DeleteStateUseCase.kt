package logic.usecases.state

import logic.repository.LogRepository
import logic.repository.ProjectRepository
import java.util.*

class DeleteStateUseCase(private val repository: ProjectRepository, val logRepository: LogRepository) {
    operator fun invoke(projectId: UUID, oldState: String): Boolean {
//        throw  ProjectNotFoundException
        val result =  repository.deleteStateById(projectId, oldState)
        logRepository.addLog(
            "delete project state named ${oldState}",
            projectId

        )
        return result

    }
}

