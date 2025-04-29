package logic.usecases.task

import logic.entities.Task
import logic.repository.TaskRepository

class CreateTaskUseCase(private val repository: TaskRepository) {

    fun createTask(task: Task): Boolean {
        //todo:
        // 1. check if the project id is exist or not
        // 2. check if the creation time is not before or after now
        // 3. call create task from TaskRepository
        // 4. return true -> (that is mean there no exceptions thrown)

        return true
    }
}
