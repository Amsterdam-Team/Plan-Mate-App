package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.TaskRepository
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class GetAllTasksByProjectIdUseCase(
    private val repository: TaskRepository,
    private val validateInputUseCase: ValidateInputUseCase
) {
    suspend operator fun invoke(projectId: String): List<Task> {
        if (!validateInputUseCase.isValidUUID(uuid = projectId)) {
            throw InvalidProjectIDException
        }
        val tasks = repository.getAllTasksByProjectId(UUID.fromString(projectId))
        if (tasks.isEmpty()) throw TaskNotFoundException
        return tasks
    }
}