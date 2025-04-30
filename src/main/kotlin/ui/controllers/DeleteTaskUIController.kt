package ui.controllers

import logic.entities.User
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.DeleteTaskUseCase

class DeleteTaskUIController(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
) {
     fun start() {}
}