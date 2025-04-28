package data.datasources.change

import logic.entities.Change
import logic.entities.ProjectChangeLog
import logic.entities.TaskChangeLog
import java.util.*

class ChangeCsvLogDataSource() : ChangeLogDataSource {
    override fun addTaskChangeLog(taskId: UUID, message: String, date: Date) {
        TODO("Not yet implemented")
    }

    override fun getTaskChangeLogsById(taskId: UUID): List<TaskChangeLog> {
        TODO("Not yet implemented")
    }

    override fun addProjectChangeLog(projectId: UUID, message: String, date: Date) {
        TODO("Not yet implemented")
    }

    override fun getProjectChangeLogsById(projectId: UUID): List<ProjectChangeLog> {
        TODO("Not yet implemented")
    }

}