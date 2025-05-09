package data.datasources.logDataSource

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.LogItem
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import java.util.*

class LogDataSource(
    private val logsCollection: MongoCollection<LogItem>
): ILogDataSource {
    override suspend fun getAllLogs(): List<LogItem> {
        return logsCollection.find().toList()
    }

    override suspend fun getLogsByEntityId(entityId: UUID): List<LogItem> {
        return logsCollection.find(Filters.eq("entityId", entityId)).toList()
    }

    override suspend fun getLogById(logId: UUID): LogItem {
        return logsCollection.find(Filters.eq("id", logId)).firstOrNull()
            ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertLog(logItem: LogItem): Boolean {
        val existingLog = logsCollection.find(Filters.eq("id", logItem.id)).firstOrNull()
        if (existingLog != null) return false

        return logsCollection.insertOne(logItem).wasAcknowledged()
    }

    override suspend fun deleteLog(logId: UUID): Boolean {
        val result = logsCollection.deleteOne(Filters.eq("id", logId))
        return result.deletedCount > 0
    }

    override suspend fun replaceAllLogs(logs: List<LogItem>): Boolean {
        val hasDuplicateIds = logs.map { it.id }.toSet().size != logs.size
        if (hasDuplicateIds) return false

        logsCollection.deleteMany(Document())
        val result = logsCollection.insertMany(logs)
        return result.wasAcknowledged() && result.insertedIds.size == logs.size
    }
}