package data.repository.log

import data.datasources.logDataSource.LogDataSourceInterface
import logic.entities.LogItem
import logic.repository.LogRepository
import java.util.*

class LogRepositoryImpl(private val logDataSource: LogDataSourceInterface): LogRepository {
    override fun viewLogsById(entityId: UUID) =
        logDataSource.getLogsByEntityId(entityId)

    override fun viewAllLogs() =
        logDataSource.getAllLogs()

    override fun addLog(log: LogItem) =
        logDataSource.insertLog(log)
}