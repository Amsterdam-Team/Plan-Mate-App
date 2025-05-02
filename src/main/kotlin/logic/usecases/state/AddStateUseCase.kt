package logic.usecases.state

import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import utils.ResultStatus
import java.util.UUID

class AddStateUseCase(
    private val repository: ProjectRepository
) {

    fun execute(projectId: UUID, state: String): ResultStatus<Unit> {
        if (state.isBlank()) return ResultStatus.Error(InvalidStateNameException)

        return runCatching {
            repository.addStateById(projectId, state)
            ResultStatus.Success(Unit)
        }.getOrElse { exception ->
            when (exception) {
                is ProjectNotFoundException -> ResultStatus.Error(exception)
                else -> ResultStatus.Error(InvalidStateNameException)
            }
        }
    }
}