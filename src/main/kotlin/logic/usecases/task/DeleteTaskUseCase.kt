package logic.usecases.task

import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: ITaskRepository,
    private val loggerUseCase: LoggerUseCase
) {
    suspend fun execute(taskId: String?) : Boolean{
        if(taskId.isNullOrEmpty()) throw InvalidTaskIDException
        val taskUUID = try {
            UUID.fromString(taskId)
        }catch (_:IllegalArgumentException){
            throw InvalidTaskIDException
        }
        return taskRepository.deleteTask(taskUUID).also { isDeleted ->
            if(isDeleted) loggerUseCase.createLog("$DELETED_KEYWORD ${taskRepository.getTaskById(taskUUID).name} $TASK_KEYWORD",taskUUID)
        }
    }

    companion object{
        const val  DELETED_KEYWORD = "deleted"
        const val  TASK_KEYWORD = "task"
    }
}