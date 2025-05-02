package logic.usecases

import logic.entities.LogItem
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.LogRepository
import java.util.UUID

class ViewTaskLogsUseCase(
    private val logRepository : LogRepository
) {

    fun viewTaskLogs(taskId : String) : List<LogItem>{
        val isValid = isValidUUID(taskId)
        if(!isValid) throw InvalidTaskIDException

        val logs = logRepository.viewLogsById(UUID.fromString(taskId))
        return logs
    }

    private fun isValidUUID(uuidString: String): Boolean {
        val uuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")
        return uuidRegex.matches(uuidString)
    }


}