package data.datasources.logDataSource

import data.datasources.CsvDataSource
import logic.entities.LogItem
import java.util.*

class LogCsvDataSource(
    private val csvDataSource: CsvDataSource<LogItem>

) : LogDataSourceInterface {
    override fun getAllLogs(): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun getLogsByEntityId(entityId: UUID): List<LogItem> {
        TODO("Not yet implemented")
    }

    override fun getLogById(logId: UUID): LogItem {
        TODO("Not yet implemented")
    }

    override fun insertLog(logItem: LogItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteLog(logId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun replaceAllLogs(logs: List<LogItem>): Boolean {
        TODO("Not yet implemented")
    }
}