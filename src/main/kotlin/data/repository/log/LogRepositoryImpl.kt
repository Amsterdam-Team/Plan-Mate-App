package data.repository.log

import data.datasources.DataSource
import logic.entities.LogItem
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.repository.LogRepository
import java.util.*

class LogRepositoryImpl(private val dataSource: DataSource): LogRepository {
    override fun viewLogsById(id: UUID): List<LogItem> {
        val logs = (dataSource.getAll() as List<LogItem>).filter { it.entityId == id }
        if(logs.isNullOrEmpty()) throw EmptyDataException
        return logs
    }

    override fun viewAllLogs(): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun addLog(log: LogItem) {
        TODO("Not yet implemented")
    }
}