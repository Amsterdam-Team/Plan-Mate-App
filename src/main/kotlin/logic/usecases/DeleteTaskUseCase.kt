package logic.usecases

import logic.entities.Task
import logic.exception.PlanMateException
import logic.repository.LogRepository
import logic.repository.TaskRepository
import utils.ResultStatus
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository
) {
    fun execute(taskId: UUID, performedBy: String): ResultStatus<Unit> {
        return ResultStatus.Error(PlanMateException.NotFoundException.TaskNotFoundException)
    }
}