package logic.repository

import logic.entities.Task
import java.util.*

interface TaskRepository {
    fun createTask(task: Task)
    fun deleteTask(taskId: UUID): Boolean
    fun getTaskById(taskId : UUID) : Task
    fun getAllTasksByProjectId(projectId: UUID): List<Task>
    fun updateTask(task: Task): Boolean

    fun updateStateNameByID(taskId:UUID,newName:String): Boolean
    fun updateTaskNameByID(taskId:UUID,newName:String): Boolean
}
