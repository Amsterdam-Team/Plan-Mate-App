package logic.usecases

import logic.entities.LogItem
import logic.repository.LogRepository
import java.util.UUID

class ViewProjectHistoryUseCase(
    private val logRepository: LogRepository
) {
    fun execute(projectId: String?): List<LogItem> {
        return emptyList()
    }
}