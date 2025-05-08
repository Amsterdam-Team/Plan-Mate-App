package logic.usecases.logs

import logic.entities.LogItem
import logic.exception.PlanMateException
import logic.repository.LogRepository
import java.util.UUID

class GetProjectHistoryUseCase(
    private val logRepository: LogRepository
) {
    suspend fun execute(projectId: String?): List<LogItem> {
        if (projectId.isNullOrBlank()) throw PlanMateException.ValidationException.InvalidProjectIDException

        val projectUUID = try {
            UUID.fromString(projectId)
        } catch (_: IllegalArgumentException) {
            throw PlanMateException.ValidationException.InvalidProjectIDException
        }

        return logRepository.viewLogsById(projectUUID)
    }
}