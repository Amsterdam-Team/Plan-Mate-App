package data.datasources.taskDataSource

import logic.entities.Task
import java.util.UUID

interface ITaskDataSource {

    suspend fun getAllTasks(): List<Task>

    suspend fun getAllProjectTasks(projectId: UUID): List<Task>

    suspend fun getTaskById(taskId: UUID): Task

    suspend fun insertTask(task: Task): Boolean

    suspend fun deleteTask(taskId: UUID): Boolean

    suspend fun getTaskState(taskId: UUID): String

    suspend fun updateTaskName(taskId: UUID, newName: String): Boolean

    suspend fun updateTaskState(taskId: UUID, newState: String): Boolean
}