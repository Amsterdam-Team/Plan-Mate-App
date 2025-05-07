package logic.usecases.task

import logic.entities.Task
import logic.repository.TaskRepository
import java.util.*

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: UUID): Task {
        return repository.getTaskById(taskId)

    }
}


