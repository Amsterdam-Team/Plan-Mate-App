package data.repository.log

import data.datasources.logDataSource.ILogDataSource
import logic.entities.LogItem
import logic.repository.LogRepository
import java.util.*

class LogRepositoryImpl(private val logDataSource: ILogDataSource): LogRepository {
    override suspend fun viewLogsById(entityId: UUID) =
        logDataSource.getLogsByEntityId(entityId)

    override suspend fun viewAllLogs() =
        logDataSource.getAllLogs()

    override suspend fun addLog(log: LogItem) =
        logDataSource.insertLog(log)
}