package logic.usecases.task

import logic.entities.Task
import logic.repository.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(taskId: String): Task {
        return repository.getTaskById(taskId)

    }
}


