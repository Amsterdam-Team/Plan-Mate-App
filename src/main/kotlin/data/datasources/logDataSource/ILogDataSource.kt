package data.datasources.logDataSource

import logic.entities.LogItem
import java.util.UUID

interface ILogDataSource {
    suspend fun getAllLogs(): List<LogItem>

    suspend fun getLogsByEntityId(entityId: UUID): List<LogItem>

    suspend fun getLogById(logId: UUID): LogItem

    suspend fun insertLog(logItem: LogItem): Boolean

    suspend fun deleteLogBy(logId: UUID): Boolean
}