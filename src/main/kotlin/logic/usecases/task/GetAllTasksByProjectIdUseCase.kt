package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.TaskRepository
import logic.usecases.ValidateInputUseCase
import java.util.*

class GetAllTasksByProjectIdUseCase(
    private val repository: TaskRepository,
    private val validateInputUseCase: ValidateInputUseCase
) {
    suspend operator fun invoke(projectId: String): List<Task> {
        if (!validateInputUseCase.isValidUUID(uuid = projectId)) {
            throw InvalidProjectIDException
        }
        return repository.getAllTasksByProjectId(UUID.fromString(projectId))
    }
}