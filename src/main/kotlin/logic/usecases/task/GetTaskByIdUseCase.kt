package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.TaskRepository
import logic.usecases.ValidateInputUseCase
import java.util.*

class GetTaskByIdUseCase(
    private val repository: TaskRepository,
    private val validateInputUseCase: ValidateInputUseCase

) {
    suspend operator fun invoke(taskId: String): Task {

        if (!validateInputUseCase.isValidUUID(uuid = taskId)) {
            throw InvalidTaskIDException
        }
        return repository.getTaskById(UUID.fromString(taskId))

    }
}


