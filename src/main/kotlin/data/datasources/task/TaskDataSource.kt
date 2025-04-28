package org.amsterdam.planmate.data.datasources.task

import logic.entities.State
import logic.entities.Task
import java.util.*

interface TaskDataSource {
    fun addTask(projectId: UUID, taskName: String, taskState: State)
    fun deleteTask(id: UUID)
    fun updateTaskState(id: UUID, newState: State)

    fun getTaskById(id: UUID): Task
    fun getAllTasks(): List<Task>

}