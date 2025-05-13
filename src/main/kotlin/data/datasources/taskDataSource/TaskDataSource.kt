package data.datasources.taskDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import java.util.*

class TaskDataSource(
    private val tasksCollection: MongoCollection<Task>
) : ITaskDataSource {
    override suspend fun getAllTasks(): List<Task> {
        return tasksCollection.find().toList()
    }

    override suspend fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return tasksCollection.find(Filters.eq(FIELD_PROJECT_ID, projectId)).toList()
    }

    override suspend fun getTaskById(taskId: UUID): Task {
        return tasksCollection.find(Filters.eq(FIELD_TASK_ID, taskId)).firstOrNull()
            ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertTask(task: Task): Boolean {
        val updateResult = tasksCollection.updateOne(
            Filters.and(
                Filters.eq(FIELD_PROJECT_ID, task.projectId),
                Filters.eq(FIELD_NAME, task.name)
            ),
            Updates.setOnInsert(FIELD_TASK_ID, task.id),
            UpdateOptions().upsert(true)
        )

        return updateResult.upsertedId != null
    }

    override suspend fun deleteTaskById(taskId: UUID): Boolean {
        val deleteResult = tasksCollection.deleteOne(Filters.eq(FIELD_TASK_ID, taskId))
        return deleteResult.deletedCount > 0
    }

    override suspend fun getTaskStateById(taskId: UUID): String {
        val task =
            tasksCollection.find(Filters.eq(FIELD_TASK_ID, taskId)).firstOrNull() ?: throw ObjectDoesNotExistException
        return task.state
    }

    override suspend fun updateTaskNameById(taskId: UUID, newName: String): Boolean {
        val task = tasksCollection.find(Filters.eq(FIELD_TASK_ID, taskId)).firstOrNull() ?: return false
        val duplicateInSameProject = tasksCollection.find(
            Filters.and(
                Filters.eq(FIELD_PROJECT_ID, task.projectId),
                Filters.eq(FIELD_NAME, newName),
                Filters.ne(FIELD_TASK_ID, taskId)
            )
        ).firstOrNull()


        if (duplicateInSameProject != null) return false

        val updateResult = tasksCollection.updateOne(
            Filters.eq(FIELD_TASK_ID, taskId),
            Updates.set(FIELD_NAME, newName)
        )

        return updateResult.modifiedCount > 0
    }

    override suspend fun updateTaskStateById(taskId: UUID, newState: String): Boolean {
        val updateResult = tasksCollection.updateOne(
            Filters.eq(FIELD_TASK_ID, taskId),
            Updates.set(FIELD_STATE, newState)
        )
        return updateResult.modifiedCount > 0
    }

    override suspend fun hasTasksWithState(projectId: UUID, state: String): Boolean {
        val query = and(
            eq(FIELD_PROJECT_ID, projectId),
            eq(FIELD_STATE, state)
        )
        return tasksCollection.countDocuments(query) > 0
    }

    private companion object {
        const val FIELD_TASK_ID = "id"
        const val FIELD_PROJECT_ID = "projectId"
        const val FIELD_NAME = "name"
        const val FIELD_STATE = "state"
    }
}