package logic.usecases

import logic.entities.LogItem
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.LogRepository
import java.util.UUID

class ViewTaskLogsUseCase(
    private val logRepository : LogRepository
) {

    fun viewTaskLogs(taskId : String) : List<LogItem>{
        val uuid = validateUUIDFormat(taskId)
        val logs = logRepository.viewLogsById(uuid)
        return logs
    }

    private fun validateUUIDFormat(uuidString: String): UUID {
        return try {
            UUID.fromString(uuidString.trim())
        } catch (e: IllegalArgumentException) {
            throw InvalidTaskIDException
        }
    }
}