package logic.usecases.task

import logic.entities.Task
import logic.repository.TaskRepository
import java.util.*

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(taskId: UUID): Task {
        val taks = repository.getTaskById(taskId)
        return taks

    }
}


