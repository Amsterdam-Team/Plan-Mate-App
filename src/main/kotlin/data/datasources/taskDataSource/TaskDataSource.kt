package data.datasources.taskDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import java.util.*

class TaskDataSource(
    private val tasksCollection: MongoCollection<Task>
): ITaskDataSource {
    override suspend fun getAllTasks(): List<Task> {
        return tasksCollection.find().toList()
    }

    override suspend fun getAllProjectTasks(projectId: UUID): List<Task> {
        return tasksCollection.find(Filters.eq("projectId", projectId)).toList()
    }

    override suspend fun getTaskById(taskId: UUID): Task {
        return tasksCollection.find(Filters.eq("id", taskId)).firstOrNull() ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertTask(task: Task): Boolean {
        val existingTask = tasksCollection.find(
            Filters.or(
                Filters.eq("id", task.id),
                Filters.and(
                    Filters.eq("projectId", task.projectId),
                    Filters.eq("name", task.name)
                )
            )
        ).firstOrNull()

        if (existingTask != null) return false

        return tasksCollection.insertOne(task).wasAcknowledged()
    }

    override suspend fun deleteTask(taskId: UUID): Boolean {
        val result = tasksCollection.deleteOne(Filters.eq("id", taskId))
        return result.deletedCount > 0
    }

    override suspend fun getTaskState(taskId: UUID): String {
        val task = tasksCollection.find(Filters.eq("id", taskId)).firstOrNull() ?: throw ObjectDoesNotExistException
        return task.state
    }

    override suspend fun updateTaskName(taskId: UUID, newName: String): Boolean {
        val task = tasksCollection.find(Filters.eq("id", taskId)).firstOrNull() ?: return false
        val duplicateInSameProject = tasksCollection.find(
            Filters.and(
                Filters.eq("projectId", task.projectId),
                Filters.eq("name", newName),
                Filters.ne("id", taskId)
            )
        ).firstOrNull()


        if (duplicateInSameProject != null) return false

        val result = tasksCollection.updateOne(
            Filters.eq("id", taskId),
            Updates.set("name", newName)
        )

        return result.modifiedCount > 0
    }

    override suspend fun updateTaskState(taskId: UUID, newState: String): Boolean {
        val result = tasksCollection.updateOne(
            Filters.eq("id", taskId),
            Updates.set("state", newState)
        )
        return result.modifiedCount > 0
    }

    override suspend fun replaceAllTasks(tasks: List<Task>): Boolean {

        val uniqueIds = tasks.map { it.id }.toSet()
        if (uniqueIds.size != tasks.size) return false

        val hasDuplicateNameInProject = tasks
            .groupBy { it.projectId }
            .values
            .any { tasksInProject ->
                tasksInProject.map { it.name }.toSet().size != tasksInProject.size
            }

        if (hasDuplicateNameInProject) return false

        tasksCollection.deleteMany(Document())
        tasksCollection.insertMany(tasks)
        return true
    }
}