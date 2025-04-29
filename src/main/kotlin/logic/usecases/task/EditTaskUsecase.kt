package logic.usecases.task

import logic.repository.TaskRepository
import java.util.*

class EditTaskUsecase(val taskRepository: TaskRepository) {
    fun editTask(taskId: String, newName:String) {
        throw Exception("unimplemented yet")
    }
}