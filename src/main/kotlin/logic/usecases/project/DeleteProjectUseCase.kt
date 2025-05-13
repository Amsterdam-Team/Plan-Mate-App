package logic.usecases.project


import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import java.util.*

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val stateManager: StateManager,
    private val validateInputUseCase: ValidateInputUseCase,
) {

    suspend fun deleteProject(projectId: String): Boolean {
        if (!stateManager.getLoggedInUser().isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException
        val projectUUID = UUID.fromString(projectId)
        val project = projectRepository.getProject(projectUUID)
        return projectRepository.deleteProject(projectUUID)
    }


}