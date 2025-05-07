package logic.usecases.task

import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.TaskRepository
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend fun execute(taskId: String?) : Boolean{
        if(taskId.isNullOrEmpty()) throw InvalidTaskIDException
        val taskUUID = try {
            UUID.fromString(taskId)
        }catch (_:IllegalArgumentException){
            throw InvalidTaskIDException
        }
        return taskRepository.deleteTask(taskUUID)
    }
}