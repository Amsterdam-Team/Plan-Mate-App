package logic.usecases.task

import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.TaskRepository
import logic.usecases.logs.LoggerUseCase
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
    private val loggerUseCase: LoggerUseCase
) {
    suspend fun execute(taskId: String?) : Boolean{
        if(taskId.isNullOrEmpty()) throw InvalidTaskIDException
        val taskUUID = try {
            UUID.fromString(taskId)
        }catch (_:IllegalArgumentException){
            throw InvalidTaskIDException
        }
        val task = taskRepository.getTaskById(taskUUID)
        val isDeleted = taskRepository.deleteTask(taskUUID)

        if (isDeleted) {
            loggerUseCase.createLog("Deleted task '${task.name}'", taskUUID)
        }

        return isDeleted
    }
}