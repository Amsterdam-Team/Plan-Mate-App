package data.datasources.taskDataSource

import data.datasources.CsvDataSource
import logic.entities.Task
import java.util.*

class TaskCsvDataSource(
    private val csvDataSource: CsvDataSource<Task>
) : TaskDataSourceInterface {
    override fun getAllTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun getAllProjectTasks(projectId: UUID): List<Task> {
        TODO("Not yet implemented")
    }

    override fun getTaskById(taskId: UUID): Task {
        TODO("Not yet implemented")
    }

    override fun insertTask(task: Task): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteTask(taskId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTaskState(taskId: UUID): String {
        TODO("Not yet implemented")
    }

    override fun updateTaskName(taskId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateTaskState(taskId: UUID, newState: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun replaceAllTasks(tasks: List<Task>): Boolean {
        TODO("Not yet implemented")
    }
}