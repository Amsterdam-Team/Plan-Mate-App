package data.repository.task

import data.datasources.DataSource
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.DataSourceException.EmptyFileException

import logic.repository.TaskRepository
import java.util.*

class TaskRepositoryImpl(val dataSource: DataSource): TaskRepository {
    override fun createTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task) {
        val allTasks =  try {
            dataSource.getAll().map { it as Task }
        } catch (e: EmptyFileException){
            throw EmptyDataException
        }catch (e: Exception){
            throw Exception("error getting all tasks : ${e.message}")
        }
        val task = allTasks.find { it.id == task.id } ?: throw TaskNotFoundException

        val newUpdatedTaskList = allTasks.filterNot { it.id == task.id}.toMutableList()
        newUpdatedTaskList.add(task)
        dataSource.saveAll(newUpdatedTaskList)
    }

    override fun updateTaskNameByID(taskId: UUID, newName: String) {

        val allTasks = dataSource.getAll().map { it as Task }
       if (allTasks.isEmpty()) throw EmptyDataException
        val task = allTasks.find { it.id == taskId } ?: throw TaskNotFoundException
        val updatedTask = task.copy(name = newName)
        val newUpdatedTaskList = allTasks.filterNot { it.id == taskId}.toMutableList()
        newUpdatedTaskList.add(updatedTask)
        dataSource.saveAll(newUpdatedTaskList)
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
        return try {
            dataSource.getById(taskId) as Task
        } catch (e: ObjectDoesNotExistException){
            throw TaskNotFoundException
        }
    }

    override fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return dataSource.getAll().map { it as Task }.filter { it.projectId == projectId }
    }


}