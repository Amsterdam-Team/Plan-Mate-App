package logic.usecases

import logic.entities.LogItem
import logic.repository.LogRepository
import java.util.UUID

class ViewTaskLogsUseCase(
    private val logRepository : LogRepository
) {

    fun viewTaskLogs(taskId : UUID) : List<LogItem>{
        return emptyList()
    }

}