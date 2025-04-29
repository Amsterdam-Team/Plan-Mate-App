package data.repository.task

import data.datasources.DataSource
import logic.entities.Task
import logic.repository.TaskRepository
import java.util.UUID

class TaskRepositoryImpl(val dataSource: DataSource<Task>): TaskRepository {
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
        TODO("Not yet implemented")
    }

    override fun getTaskById(taskId: UUID): Task {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByProjectId(projectId: String): List<Task> {
        TODO("Not yet implemented")
    }


}