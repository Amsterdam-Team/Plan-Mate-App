package logic.usecases.state


import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import logic.usecases.StateManager
import logic.usecases.ValidateInputUseCase
import java.util.UUID

class AddStateUseCase(
    private val repository: ProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val stateManager: StateManager,
    ) {

    suspend fun execute(projectId: String, state: String): Boolean{
        val user = stateManager.getLoggedInUser()
        if(!user.isAdmin) throw AdminPrivilegesRequiredException
        if(!validateInputUseCase.isValidName(state)) throw InvalidStateNameException
        if(!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException
        return repository.addStateById(UUID.fromString(projectId), state)
    }
}