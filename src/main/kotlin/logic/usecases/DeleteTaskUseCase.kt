package logic.usecases

import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import java.util.UUID

class DeleteTaskUseCase(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
) {
    fun execute(projectUUID: String?,taskId: String?) {
        //verifying id's
        //verifying if this project is exist
        //verifying if this task is exist
        //if the task exist will delete it
    }
}