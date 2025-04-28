package org.amsterdam.planmate.data.datasources.task

import logic.entities.State
import logic.entities.Task
import java.util.*

class TaskCsvDataSource : TaskDataSource {
    override fun addTask(projectId: UUID, taskName: String, taskState: State) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun updateTaskState(id: UUID, newState: State) {
        TODO("Not yet implemented")
    }

    override fun getTaskById(id: UUID): Task {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): List<Task> {
        TODO("Not yet implemented")
    }
}