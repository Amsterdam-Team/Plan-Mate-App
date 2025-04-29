package data.repository.log

import logic.entities.LogItem
import logic.repository.LogRepository
import java.util.UUID

class LogRepositoryImpl: LogRepository {
    override fun viewLogsById(id: UUID): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun viewAllLogs(): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun addLog(log: LogItem) {
        TODO("Not yet implemented")
    }
}