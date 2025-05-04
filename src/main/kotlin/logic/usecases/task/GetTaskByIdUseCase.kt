package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
import java.util.*

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(taskId: UUID): Task {
        val task = try {
            repository.getTaskById(taskId)
        } catch (e: TaskNotFoundException) {
            throw e
        }
        if (task.id != taskId) {
            throw TaskNotFoundException
        }

        return task
    }
}



