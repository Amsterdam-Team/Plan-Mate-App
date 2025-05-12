package logic.usecases.project


import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.IProjectRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class DeleteProjectUseCase(
    private val projectRepository: IProjectRepository,
    private val stateManager: StateManager,
    private val validateInputUseCase: ValidateInputUseCase,
    private val loggerUseCase: LoggerUseCase
) {

    suspend fun deleteProject(projectId: String): Boolean {
        if (!stateManager.getLoggedInUser().isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException
        val projectUUID = UUID.fromString(projectId)
        val project = projectRepository.getProject(projectUUID)
        return projectRepository.deleteProject(projectUUID).also { isDeleted ->
            if (isDeleted) loggerUseCase.createLog("deleted ${project.name} Project", projectUUID)
        }
    }


}