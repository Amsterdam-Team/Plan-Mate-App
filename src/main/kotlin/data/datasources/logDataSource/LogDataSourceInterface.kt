package data.datasources.logDataSource

import logic.entities.LogItem
import java.util.UUID

interface LogDataSourceInterface {
    fun getAllLogs(): List<LogItem>

    fun getLogsByEntityId(entityId: UUID): List<LogItem>

    fun getLogById(logId: UUID): LogItem

    fun insertLog(logItem: LogItem): Boolean

    fun deleteLog(logId: UUID): Boolean

    fun replaceAllLogs(logs: List<LogItem>): Boolean
}