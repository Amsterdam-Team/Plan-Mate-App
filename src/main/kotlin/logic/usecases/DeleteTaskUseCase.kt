package logic.usecases

import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.LogRepository
import logic.repository.TaskRepository
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
    val logRepository: LogRepository
) {
    fun execute(taskId: String?) {
        if(taskId.isNullOrEmpty()) throw InvalidTaskIDException
        val taskUUID = try {
            UUID.fromString(taskId)
        }catch (_:IllegalArgumentException){
            throw InvalidTaskIDException
        }
        taskRepository.deleteTask(taskUUID)
        logRepository.addLog(
            "delete task of id ${taskId}",
            UUID.fromString(taskId)

        )
    }
}