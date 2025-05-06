package data.datasources.logDataSource

import com.mongodb.kotlin.client.coroutine.MongoCollection
import logic.entities.LogItem
import java.util.*

class LogRemoteDataSource(
    private val logsCollection: MongoCollection<LogItem>
): LogDataSourceInterface {
    override suspend fun getAllLogs(): List<LogItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getLogsByEntityId(entityId: UUID): List<LogItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getLogById(logId: UUID): LogItem {
        TODO("Not yet implemented")
    }

    override suspend fun insertLog(logItem: LogItem): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLog(logId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun replaceAllLogs(logs: List<LogItem>): Boolean {
        TODO("Not yet implemented")
    }
}