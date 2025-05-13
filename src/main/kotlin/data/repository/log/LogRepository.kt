package data.repository.log

import data.datasources.logDataSource.ILogDataSource
import logic.entities.LogItem
import logic.repository.ILogRepository
import java.util.*

class LogRepository(private val logDataSource: ILogDataSource): ILogRepository {
    override suspend fun viewLogsByLogId(id: UUID) =
        logDataSource.getLogsByEntityId(id)

    override suspend fun viewAllLogs() =
        logDataSource.getAllLogs()

    override suspend fun addLog(log: LogItem) =
        logDataSource.insertLog(log)
}