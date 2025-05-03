package data.repository.task

import data.datasources.DataSource
import data.datasources.taskDataSource.TaskDataSourceInterface
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.DataSourceException.EmptyFileException

import logic.repository.TaskRepository
import java.util.*

class TaskRepositoryImpl(private val taskDataSourceInterface: TaskDataSourceInterface): TaskRepository {
    override fun createTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task) =
        taskDataSourceInterface.updateTaskName(task.id,task.name)&&
        taskDataSourceInterface.updateTaskState(task.id,task.state)


    override fun updateTaskNameByID(taskId: UUID, newName: String) =
        taskDataSourceInterface.updateTaskName(taskId,newName)


    override fun updateStateNameByID(taskId: UUID, newState: String) =
        taskDataSourceInterface.updateTaskState(taskId,newState)


    override fun deleteTask(taskId: UUID) =
        taskDataSourceInterface.deleteTask(taskId)

    override fun getTaskById(taskId: UUID): Task =
        taskDataSourceInterface.getTaskById(taskId)



    override fun getAllTasksByProjectId(projectId: UUID) =
        taskDataSourceInterface.getAllTasks()



}