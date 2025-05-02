package logic.usecases.state

import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.TaskRepository
import java.util.UUID

class GetTaskStateUseCase(private val repository: TaskRepository) {

    fun execute(taskID: String): String {
        return if (isValidUUID(taskID)) repository.getTaskById(UUID.fromString(taskID)).state
        else throw InvalidTaskIDException
    }

    private fun isValidUUID(uuid: String) = runCatching { UUID.fromString(uuid) }.isSuccess

}