package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException.ValidationException.InvalidUUIDFormatException
import logic.repository.IProjectRepository
import logic.usecases.utils.ValidateInputUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import java.util.*

class GetProjectDetailsUseCase(
    private val projectRepository: IProjectRepository,
    private val getTasksUseCase: GetAllTasksByProjectIdUseCase,
    private val validateInputUseCase: ValidateInputUseCase

) {

    suspend operator fun invoke(projectID: String): Project {

        if (validateInputUseCase.isValidUUID(projectID)) {
            val project = projectRepository.getProjectById(UUID.fromString(projectID))
            val tasks = getTasksUseCase.invoke(projectID)
            return project.copy(
                tasks = tasks
            )
        }else{
            throw InvalidUUIDFormatException
        }

    }



}