package data.datasources.taskDataSource

import com.mongodb.kotlin.client.coroutine.MongoCollection
import logic.entities.Task
import java.util.*

class TaskRemoteDataSource(
    tasksCollection: MongoCollection<Task>
): TaskDataSourceInterface {
    override suspend fun getAllTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProjectTasks(projectId: UUID): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: UUID): Task {
        TODO("Not yet implemented")
    }

    override suspend fun insertTask(task: Task): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskState(taskId: UUID): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskName(taskId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskState(taskId: UUID, newState: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun replaceAllTasks(tasks: List<Task>): Boolean {
        TODO("Not yet implemented")
    }
}