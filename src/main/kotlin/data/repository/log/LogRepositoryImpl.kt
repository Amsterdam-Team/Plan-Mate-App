package data.repository.log

import data.datasources.CsvDataSource
import logic.entities.LogItem
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.repository.LogRepository
import java.util.UUID

class LogRepositoryImpl(private val csvDataSource: CsvDataSource<LogItem>): LogRepository {
    override fun viewLogsById(id: UUID): List<LogItem> {
        val logs = csvDataSource.getById(id) as List<LogItem>
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