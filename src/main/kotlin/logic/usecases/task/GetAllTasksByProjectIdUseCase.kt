package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException
import logic.repository.TaskRepository
import java.util.*

class GetAllTasksByProjectIdUseCase(private val repository: TaskRepository) {
    operator fun invoke(projectId: UUID): List<Task> {
        val taks = repository.getAllTasksByProjectId(projectId)
        taks.ifEmpty { throw PlanMateException.NotFoundException.TaskNotFoundException }
        return taks
    }

}