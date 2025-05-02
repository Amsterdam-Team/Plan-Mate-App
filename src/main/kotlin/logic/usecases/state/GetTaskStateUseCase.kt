package logic.usecases.state

import logic.repository.TaskRepository
import java.util.UUID

class GetTaskStateUseCase(private val repository: TaskRepository) {

    fun execute(taskID: String): String {

        return repository.getTaskById(UUID.fromString(taskID)).state
    }
}