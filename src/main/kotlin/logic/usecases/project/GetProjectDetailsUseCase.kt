package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException.ValidationException.InvalidUUIDFormatException
import logic.repository.ProjectRepository
import logic.usecases.ValidateInputUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import java.util.UUID

class GetProjectDetailsUseCase(
    private val projectRepository: ProjectRepository,
    private val getTasksUseCase: GetAllTasksByProjectIdUseCase,
    private val validateInputUseCase: ValidateInputUseCase

) {

    operator fun invoke(projectID: String): Project {

        if (validateInputUseCase.isValidUUID(projectID)) {
            val project = projectRepository.getProject(UUID.fromString(projectID))
            val tasks = getTasksUseCase.invoke(UUID.fromString(projectID))
            return project.copy(
                tasks = tasks
            )
        }else{
            throw InvalidUUIDFormatException
        }

    }



}