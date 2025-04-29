package logic.usecases

import logic.repository.LogRepository
import logic.repository.TaskRepository
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository
) {
    fun execute(taskId: UUID, performedBy: String) {
    }
}