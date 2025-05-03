package data.repository.log

import data.datasources.DataSource
import data.datasources.logDataSource.LogDataSourceInterface
import logic.entities.LogItem
import logic.exception.PlanMateException.NotFoundException.TaskLogsNotFound
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.repository.LogRepository
import java.util.*

class LogRepositoryImpl(private val logDataSourceInterface: LogDataSourceInterface): LogRepository {
    override fun viewLogsById(entityId: UUID) =
        logDataSourceInterface.getLogsByEntityId(entityId)

    override fun viewAllLogs() =
        logDataSourceInterface.getAllLogs()

    override fun addLog(log: LogItem) =
        logDataSourceInterface.insertLog(log)
}