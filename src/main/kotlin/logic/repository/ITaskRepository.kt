package logic.repository

import logic.entities.Task
import java.util.*

interface ITaskRepository {
    suspend fun createTask(task: Task): Boolean
    suspend fun deleteTask(taskId: UUID): Boolean
    suspend fun getTaskById(taskId: UUID): Task
    suspend fun getAllTasksByProjectId(projectId: UUID): List<Task>
    suspend fun updateTask(task: Task): Boolean

    suspend fun updateStateNameByID(taskId: UUID, newStateName: String): Boolean
    suspend fun updateTaskNameByID(taskId: UUID, newName: String): Boolean
    suspend fun hasTasksWithState(projectId: UUID, state: String): Boolean
}
