package logic.repository

import logic.entities.Task
import utils.ResultStatus

interface TaskRepository {
    fun createTask(task: Task): ResultStatus<Task>
    fun updateTask(task: Task): ResultStatus<Task>
    fun deleteTask(taskId: String): ResultStatus<Task>
    fun getTasks():ResultStatus<List<Task>>
    fun getAllTasksByProjectId(projectId: String): ResultStatus<List<Task>>
    fun getAllTasksByUserId(userId: String): ResultStatus<List<Task>>

}