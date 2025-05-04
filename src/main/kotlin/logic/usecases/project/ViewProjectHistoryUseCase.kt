package logic.usecases.project

import logic.entities.LogItem
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.LogRepository
import java.util.UUID

class ViewProjectHistoryUseCase(
    private val logRepository: LogRepository
) {
    fun execute(projectId: String?): List<LogItem> {
        if (projectId.isNullOrBlank()) throw InvalidProjectIDException

        val projectUUID = try {
            UUID.fromString(projectId)
        } catch (_: IllegalArgumentException) {
            throw InvalidProjectIDException
        }

        return logRepository.viewLogsById(projectUUID)
    }
}