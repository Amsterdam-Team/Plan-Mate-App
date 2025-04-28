package org.amsterdam.planmate.data.repository.task

import logic.entities.Task
import logic.repository.TaskRepository
import utils.ResultStatus

class TaskRepositoryImpl: TaskRepository {
    override fun createTask(task: Task): ResultStatus<Task> {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task): ResultStatus<Task> {
        TODO("Not yet implemented")
    }

    override fun deleteTask(taskId: String): ResultStatus<Task> {
        TODO("Not yet implemented")
    }

    override fun getTasks(): ResultStatus<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByProjectId(projectId: String): ResultStatus<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByUserId(userId: String): ResultStatus<List<Task>> {
        TODO("Not yet implemented")
    }

}