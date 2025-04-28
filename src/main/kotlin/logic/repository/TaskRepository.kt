package logic.repository

import logic.entities.Task
import java.util.*

interface TaskRepository {
    fun createTask(task: Task)
    fun deleteTask(taskId: String)
    fun getTaskById(taskId : UUID) : Task
    fun getAllTasksByProjectId(projectId: String): List<Task>
    fun updateTask(task: Task)

    fun updateStateNameByID(taskId:UUID,newName:String)
    fun updateTaskNameByID(taskId:UUID,newName:String)
}
