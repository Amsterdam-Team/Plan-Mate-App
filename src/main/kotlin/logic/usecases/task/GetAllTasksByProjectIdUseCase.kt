package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
import java.util.*

class GetAllTasksByProjectIdUseCase(private val repository: TaskRepository) {
    operator fun invoke(projectId: UUID): List<Task> =
        repository.getAllTasksByProjectId(projectId)
            .filter { it.projectId == projectId }
            .ifEmpty { throw TaskNotFoundException }
}
