package logic.usecases.task

import logic.entities.Task
import logic.repository.TaskRepository
import java.util.*

class GetAllTasksByProjectIdUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(projectId: UUID): List<Task> {
        return repository.getAllTasksByProjectId(projectId)

    }

}