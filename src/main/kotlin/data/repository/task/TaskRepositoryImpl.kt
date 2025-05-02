package data.repository.task

import data.datasources.DataSource
import logic.entities.LogItem
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.LogRepository
import logic.repository.TaskRepository
import java.time.LocalDateTime.now
import java.util.*

class TaskRepositoryImpl(val dataSource: DataSource, val logRepository: LogRepository): TaskRepository {
    override fun createTask(task: Task) {

    }

    override fun updateTask(task: Task) {
    }

    override fun updateTaskNameByID(taskId: UUID, newName: String) {
    }

    override fun updateStateNameByID(taskId: UUID, newName: String) {
    }

    override fun deleteTask(taskId: UUID) {
        val task = try {
            dataSource.getById(taskId) as Task
        }catch (_: ObjectDoesNotExistException){
            throw TaskNotFoundException
        }
        val allTasks = dataSource.getAll() as List<Task>
        dataSource.saveAll(allTasks.filterNot { it.id == task.id })

    }

    override fun getTaskById(taskId: UUID): Task {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return dataSource.getAll().map { it as Task }.filter { it.projectId == projectId }
    }


}