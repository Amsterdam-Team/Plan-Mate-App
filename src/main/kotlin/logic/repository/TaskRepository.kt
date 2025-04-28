package logic.repository

import logic.entities.State
import logic.entities.Task
import utils.ResultStatus
import java.util.*

interface TaskRepository {
    fun addTask(projectId: UUID, taskName: String, taskState: State)
    fun deleteTask(id: UUID)
    fun updateTaskState(id: UUID, newState: State)

    fun getTaskById(id: UUID): Task
    fun getAllTasks(): List<Task>
}