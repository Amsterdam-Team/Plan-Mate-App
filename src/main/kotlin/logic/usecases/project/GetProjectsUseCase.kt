package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidUUIDFormatException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import ui.utils.Validator
import java.util.*

class GetProjectsUseCase(
    private val projectRepository: ProjectRepository,
    private val getTasksUseCase: GetAllTasksByProjectIdUseCase
) {

    operator fun invoke(projectID: String): Project {
        val projectUUID = Validator.isUUIDValid(projectID)
        val project = projectRepository.getProject(projectUUID)
        val tasks = getTasksUseCase.invoke(projectUUID)
        return project.copy(
            tasks = tasks
        )

    }


}