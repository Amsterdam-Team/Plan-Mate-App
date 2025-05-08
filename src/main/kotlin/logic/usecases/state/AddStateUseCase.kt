package logic.usecases.state

import logic.entities.User
import logic.exception.PlanMateException
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ProjectRepository
import logic.usecases.ValidateInputUseCase
import utils.ResultStatus
import java.util.UUID

class AddStateUseCase(
    private val repository: ProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val user: User
) {

    suspend fun execute(projectId: String, state: String): Boolean{
        if(!validateInputUseCase.isValidName(state)) throw InvalidStateNameException
        if(!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException
        if(!user.isAdmin) throw AdminPrivilegesRequiredException
        return repository.addStateById(UUID.fromString(projectId), state)

    }
}