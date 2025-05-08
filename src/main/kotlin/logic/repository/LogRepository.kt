package logic.repository

import logic.entities.LogItem
import java.util.*

interface LogRepository {
    suspend fun viewLogsById(id : UUID) : List<LogItem>
    suspend fun viewAllLogs(): List<LogItem>
    suspend fun addLog(log : LogItem): Boolean
}