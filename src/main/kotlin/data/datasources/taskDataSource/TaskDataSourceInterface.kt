package data.datasources.taskDataSource

import logic.entities.Task
import java.util.UUID

interface TaskDataSourceInterface {

    fun getAllTasks(): List<Task>

    fun getAllProjectTasks(projectId: UUID): List<Task>

    fun getTaskById(taskId: UUID): Task

    fun insertTask(task: Task): Boolean

    fun deleteTask(taskId: UUID): Boolean

    fun getTaskState(taskId: UUID): String

    fun updateTaskName(taskId: UUID, newName: String): Boolean

    fun updateTaskState(taskId: UUID, newState: String): Boolean

    fun replaceAllTasks(tasks: List<Task>): Boolean
}