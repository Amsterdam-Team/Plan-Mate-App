package logic.usecases.project

import logic.repository.ProjectRepository
import ui.utils.ResultStatus
import java.util.UUID

class CreateStateUseCase(private val repository: ProjectRepository) {

    fun execute(projectId: UUID, stateName: String): ResultStatus<Unit> {
        return ResultStatus.Success(Unit)
    }
}