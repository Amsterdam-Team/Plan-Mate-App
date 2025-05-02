package data.repository.task

import data.datasources.DataSource
import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
import java.util.*

class TaskRepositoryImpl(val dataSource: DataSource): TaskRepository {
    override fun createTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task) {
        throw Exception("unimplemented yet")
    }

    override fun updateTaskNameByID(taskId: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun updateStateNameByID(taskId: UUID, newName: String) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }


}