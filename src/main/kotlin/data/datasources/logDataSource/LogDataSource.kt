package data.datasources.logDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.LogItem
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import java.util.*

class LogDataSource(private val logsCollection: MongoCollection<LogItem>): ILogDataSource {

    override suspend fun getAllLogs(): List<LogItem> {
        return logsCollection.find().toList()
    }

    override suspend fun getLogsByEntityId(entityId: UUID): List<LogItem> {
        return logsCollection.find(Filters.eq(FIELD_ENTITY_ID, entityId)).toList()
    }

    override suspend fun getLogById(logId: UUID): LogItem {
        return logsCollection.find(Filters.eq(FIELD_LOG_ID, logId)).firstOrNull()
            ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertLog(logItem: LogItem): Boolean {
        val insertResult = logsCollection.updateOne(
            Filters.eq(FIELD_LOG_ID, logItem.id),
            Updates.combine(
                Updates.setOnInsert(FIELD_LOG_ID, logItem.id),
                Updates.setOnInsert(FIELD_ENTITY_ID, logItem.entityId),
                Updates.setOnInsert(FIELD_TIMESTAMP, logItem.date),
                Updates.setOnInsert(FIELD_MESSAGE, logItem.message)
            ),
            UpdateOptions().upsert(true)
        )
        return insertResult.upsertedId != null
    }

    override suspend fun deleteLogBy(logId: UUID): Boolean {
        val deleteResult = logsCollection.deleteOne(Filters.eq(FIELD_LOG_ID, logId))
        return deleteResult.deletedCount > 0
    }

    private companion object {
        const val FIELD_LOG_ID = "id"
        const val FIELD_ENTITY_ID = "entityId"
        const val FIELD_TIMESTAMP = "timestamp"
        const val FIELD_MESSAGE = "message"
    }
}