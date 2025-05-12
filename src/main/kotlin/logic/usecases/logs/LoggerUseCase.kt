package logic.usecases.logs

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import logic.repository.ILogRepository
import logic.usecases.utils.StateManager
import java.util.UUID

class LoggerUseCase(
    private val logRepository: ILogRepository,
    private val stateManager: StateManager,
) {

    suspend fun createLog(message:String, entityId: UUID): Boolean{
        val currentDateTime : LocalDateTime= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val formattedLogMessage = buildString {
            append(message)
            append("by ${stateManager.getLoggedInUser().username}")
            append("on ${currentDateTime.toString()}")
        }
        val logItem = LogItem(
            id = UUID.randomUUID(),
            message = formattedLogMessage,
            date = currentDateTime,
            entityId = entityId
        )
        return logRepository.addLog(logItem)
    }
}