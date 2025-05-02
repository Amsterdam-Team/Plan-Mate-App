package data.repository.log

import data.datasources.DataSource
import logic.entities.LogItem
import logic.entities.User
import logic.exception.PlanMateException.NotFoundException.TaskLogsNotFound
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.repository.LogRepository
import java.time.LocalDateTime
import java.util.*

class LogRepositoryImpl(private val dataSource : DataSource, private val user : User): LogRepository {

    override fun viewLogsById(id: UUID): List<LogItem> {
        val logs = (dataSource.getAll() as List<LogItem>).filter { it.entityId == id }
        if(logs.isNullOrEmpty()) throw TaskLogsNotFound
        return logs
    }

    override fun viewAllLogs(): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun addLog(message: String, itemId: UUID) {
        dataSource.add(
            LogItem(
            id = UUID.randomUUID(),
            message = "${message} by ${user.username}",
            date= LocalDateTime.now(),
                entityId = itemId
        ))
    }
}