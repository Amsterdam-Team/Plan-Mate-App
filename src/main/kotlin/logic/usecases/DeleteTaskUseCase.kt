package logic.usecases

import logic.repository.LogRepository
import logic.repository.TaskRepository
import java.util.UUID

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun execute(taskId: UUID) {
    }
}