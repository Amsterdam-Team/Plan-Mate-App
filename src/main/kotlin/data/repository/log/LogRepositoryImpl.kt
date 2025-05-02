package data.repository.log

import data.datasources.DataSource
import logic.entities.LogItem
import logic.exception.PlanMateException.NotFoundException.TaskLogsNotFound
import logic.repository.LogRepository
import java.util.*

class LogRepositoryImpl(private val dataSource : DataSource): LogRepository {
    override fun viewLogsById(id: UUID): List<LogItem> {
        val logs = dataSource.getAll().map { it as LogItem }.filter { it.id == id }
        if(logs.isEmpty()) throw TaskLogsNotFound
        return logs
    }

    override fun viewAllLogs(): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun addLog(log: LogItem) {
        dataSource.add(log)
    }
}