package logic.usecases.state

import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import java.util.UUID

class GetProjectStatesUseCase(private val repository: ProjectRepository) {

    suspend fun execute(projectID: String): List<String> {
        return if (isValidUUID(projectID)) repository.getProject(UUID.fromString(projectID)).states
        else throw InvalidProjectIDException
    }

    private fun isValidUUID(uuid: String) = runCatching { UUID.fromString(uuid) }.isSuccess

}