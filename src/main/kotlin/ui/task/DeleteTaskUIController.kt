package ui.task

import logic.repository.ProjectRepository
import logic.usecases.task.DeleteTaskUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView

class DeleteTaskUIController(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val projectRepository: ProjectRepository,
): BaseUIController {
    override fun execute() {
        tryToExecute(
            action = {
                println("🗂 Available Projects and Tasks:\n")
                projectRepository.getProjects().forEach {
                    printSwimlanesView(it)
                }
                deleteTaskUseCase.execute(readlnOrNull())
            },
            onSuccess = {
                println("✅ Task deleted successfully.")
            }
        )
    }
}