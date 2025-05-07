package logic.usecases.project

import logic.entities.User
import logic.exception.PlanMateException
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import logic.usecases.StateManager
import logic.usecases.ValidateInputUseCase
import java.util.UUID

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val stateManager: StateManager,
    private val validateInputUseCase: ValidateInputUseCase
) {

    suspend fun deleteProject(projectId: String): Boolean {
        if (!stateManager.getLoggedInUser().isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException
        return projectRepository.deleteProject(UUID.fromString(projectId))
    }


}