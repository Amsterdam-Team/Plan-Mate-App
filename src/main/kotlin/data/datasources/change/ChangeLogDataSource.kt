package data.datasources.change

import logic.entities.Change
import logic.entities.ProjectChangeLog
import logic.entities.TaskChangeLog
import java.util.*

interface ChangeLogDataSource {
    fun addTaskChangeLog(taskId: UUID, message: String, date: Date)
    fun getTaskChangeLogsById(taskId:UUID) : List<TaskChangeLog>
    fun addProjectChangeLog(projectId: UUID, message: String, date: Date)
    fun getProjectChangeLogsById(projectId: UUID) : List<ProjectChangeLog>
}