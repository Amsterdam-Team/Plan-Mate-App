package logic.usecases.logs

import logic.entities.LogItem
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException

import logic.repository.ILogRepository
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class ViewTaskLogsUseCase(
    private val logRepository : ILogRepository,
    private val validationInputUseCase: ValidateInputUseCase
) {

    suspend fun viewTaskLogs(taskId : String) : List<LogItem>{
        if(!validationInputUseCase.isValidUUID(taskId)) throw InvalidTaskIDException
        val logs = logRepository.viewLogsByLogId(UUID.fromString(taskId))
        return logs
    }
}