package logic.repository

import logic.entities.LogItem
import java.util.UUID

interface LogRepository {
    fun viewLogsById(id : UUID) : List<LogItem>
    fun viewAllLogs(): List<LogItem>
    fun addLog(log : LogItem)
}