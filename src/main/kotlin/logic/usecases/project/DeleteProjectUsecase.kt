package logic.usecases.project

import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import logic.usecases.ValidateInputUseCase
import java.util.UUID

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val user: User,
    private val validateInputUseCase: ValidateInputUseCase
) {

    fun deleteProject(projectId: String): Boolean {
        if (!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException
        if (!user.isAdmin) throw AdminPrivilegesRequiredException
        return projectRepository.deleteProject(UUID.fromString(projectId))
    }


}