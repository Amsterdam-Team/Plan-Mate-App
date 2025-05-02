package logic.usecases

import logic.repository.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    fun execute(taskId: String?) {
        //verifying id's
        //verifying if this task is exist
        //if the task exist will delete it
    }
}