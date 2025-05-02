package logic.usecases.task

import logic.entities.Task
import logic.repository.TaskRepository

class GetAllTasksByProjectIdUseCase(private val repository: TaskRepository) {
    operator fun invoke(projectId: String): List<Task> {
        return repository.getAllTasksByProjectId(projectId)

    }

}