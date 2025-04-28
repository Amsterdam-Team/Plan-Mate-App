package logic.repository

import logic.entities.ProjectChangeLog
import logic.entities.TaskChangeLog
import java.util.*

interface ChangeRepository {
    fun addTaskChangeLog(taskId: UUID, message: String, date: Date)
    fun getTaskChangeLogsById(taskId:UUID) : List<TaskChangeLog>
    fun addProjectChangeLog(projectId: UUID, message: String, date: Date)
    fun getProjectChangeLogsById(projectId: UUID) : List<ProjectChangeLog>
}