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
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "create task with name ${task.name}",
                date = now(),
                entityId = task.id,
            )
        )
    }

    override fun updateTask(task: Task) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "update task with name ${task.name}",
                date = now(),
                entityId = task.id,
            )
        )
    }

    override fun updateTaskNameByID(taskId: UUID, newName: String) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "update task name to be ${newName}",
                date = now(),
                entityId = taskId,
            )
        )
    }

    override fun updateStateNameByID(taskId: UUID, newName: String) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "update task state to be ${newName}",
                date = now(),
                entityId = taskId,
            )
        )
    }

    override fun deleteTask(taskId: UUID) {
        val task = try {
            dataSource.getById(taskId) as Task
        }catch (_: ObjectDoesNotExistException){
            throw TaskNotFoundException
        }
        val allTasks = dataSource.getAll() as List<Task>
        dataSource.saveAll(allTasks.filterNot { it.id == task.id })
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "delete task with id ${taskId.toString()}",
                date = now(),
                entityId = taskId,
            )
        )
    }

    override fun getTaskById(taskId: UUID): Task {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return dataSource.getAll().map { it as Task }.filter { it.projectId == projectId }
    }


}