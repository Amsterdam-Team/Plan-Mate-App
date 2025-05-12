package data.datasources.taskDataSource

import logic.entities.Task
import java.util.UUID

interface ITaskDataSource {

    suspend fun getAllTasks(): List<Task>

    suspend fun getAllTasksByProjectId(projectId: UUID): List<Task>

    suspend fun getTaskById(taskId: UUID): Task

    suspend fun insertTask(task: Task): Boolean

    suspend fun deleteTaskById(taskId: UUID): Boolean

    suspend fun getTaskStateById(taskId: UUID): String

    suspend fun updateTaskNameById(taskId: UUID, newName: String): Boolean

    suspend fun updateTaskStateById(taskId: UUID, newState: String): Boolean
}