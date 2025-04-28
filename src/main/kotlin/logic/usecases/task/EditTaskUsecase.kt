package logic.usecases.task

import logic.repository.TaskRepository
import java.util.*

class EditTaskUsecase(val taskRepository: TaskRepository) {
    fun editTask(taskId: String, newName:String) {
        taskRepository.updateTaskNameByID(taskId = UUID.fromString(taskId), newName = newName)
    }
}