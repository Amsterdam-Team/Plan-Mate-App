package ui.controllers

import logic.repository.ProjectRepository
import logic.usecases.DeleteTaskUseCase

class DeleteTaskUIController(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val projectRepository: ProjectRepository,
) {
     fun start() {}
}