package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ITaskRepository
import logic.usecases.utils.ValidateInputUseCase
import java.util.*

class GetTaskByIdUseCase(
    private val repository: ITaskRepository,
    private val validateInputUseCase: ValidateInputUseCase

) {
    suspend operator fun invoke(taskId: String): Task {

        if (!validateInputUseCase.isValidUUID(uuid = taskId)) {
            throw InvalidTaskIDException
        }
        return repository.getTaskById(UUID.fromString(taskId))

    }
}


