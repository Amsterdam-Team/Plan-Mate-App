package data.repository.task

import data.datasources.taskDataSource.ITaskDataSource
import logic.entities.Task
import logic.repository.ITaskRepository
import java.util.*

class TaskRepository(private val taskDataSource: ITaskDataSource): ITaskRepository {
    override suspend fun createTask(task: Task) =
        taskDataSource.insertTask(task)


    override suspend fun updateTask(task: Task) =
        taskDataSource.updateTaskName(task.id,task.name)&&
        taskDataSource.updateTaskState(task.id,task.state)


    override suspend fun updateTaskNameByID(taskId: UUID, newName: String) =
        taskDataSource.updateTaskName(taskId,newName)


    override suspend fun updateStateNameByID(taskId: UUID, newState: String) =
        taskDataSource.updateTaskState(taskId,newState)


    override suspend fun deleteTask(taskId: UUID) =
        taskDataSource.deleteTask(taskId)

    override suspend fun getTaskById(taskId: UUID): Task =
        taskDataSource.getTaskById(taskId)



    override suspend fun getAllTasksByProjectId(projectId: UUID) =
        taskDataSource.getAllProjectTasks(projectId)
}