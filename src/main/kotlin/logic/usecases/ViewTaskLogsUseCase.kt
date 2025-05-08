package logic.usecases

import logic.entities.LogItem
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException

import logic.repository.LogRepository
import java.util.UUID

class ViewTaskLogsUseCase(
    private val logRepository : LogRepository,
    private val validationInputUseCase: ValidateInputUseCase
) {

    suspend fun viewTaskLogs(taskId : String) : List<LogItem>{
        if(!validationInputUseCase.isValidUUID(taskId)) throw InvalidTaskIDException
        val logs = logRepository.viewLogsById(UUID.fromString(taskId))
        return logs
    }
}