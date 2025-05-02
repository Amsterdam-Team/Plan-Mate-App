package logic.usecases.task

import logic.repository.TaskRepository

class EditTaskUseCase(val taskRepository: TaskRepository) {

    fun editTask(taskId: String, newName: String):Boolean {

        return false
    }
}