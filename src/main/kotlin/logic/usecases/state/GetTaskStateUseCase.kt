package logic.usecases.state

import logic.repository.TaskRepository
import java.util.UUID

class GetTaskStateUseCase(private val repository: TaskRepository) {

    fun getTaskStateByTaskID(id: UUID): String {
        return ""
    }
}