package data.repository.task

import data.datasources.taskDataSource.ITaskDataSource
import logic.entities.Task
import logic.repository.TaskRepository
import java.util.*

class TaskRepositoryImpl(private val taskDataSource: ITaskDataSource): TaskRepository {
    override fun createTask(task: Task) =
        taskDataSource.insertTask(task)


    override fun updateTask(task: Task) =
        taskDataSource.updateTaskName(task.id,task.name)&&
        taskDataSource.updateTaskState(task.id,task.state)


    override fun updateTaskNameByID(taskId: UUID, newName: String) =
        taskDataSource.updateTaskName(taskId,newName)


    override fun updateStateNameByID(taskId: UUID, newState: String) =
        taskDataSource.updateTaskState(taskId,newState)


    override fun deleteTask(taskId: UUID) =
        taskDataSource.deleteTask(taskId)

    override fun getTaskById(taskId: UUID): Task =
        taskDataSource.getTaskById(taskId)



    override fun getAllTasksByProjectId(projectId: UUID) =
        taskDataSource.getAllTasks()



}