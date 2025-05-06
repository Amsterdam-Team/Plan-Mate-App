package data.datasources.logDataSource

import logic.entities.LogItem
import java.util.UUID

interface LogDataSourceInterface {
    suspend fun getAllLogs(): List<LogItem>

    suspend fun getLogsByEntityId(entityId: UUID): List<LogItem>

    suspend fun getLogById(logId: UUID): LogItem

    suspend fun insertLog(logItem: LogItem): Boolean

    suspend fun deleteLog(logId: UUID): Boolean

    suspend fun replaceAllLogs(logs: List<LogItem>): Boolean
}