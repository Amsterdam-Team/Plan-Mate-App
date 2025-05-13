package logic.usecases.state


import logic.exception.PlanMateException
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.IProjectRepository
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class AddStateUseCase(
    private val repository: IProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val stateManager: StateManager,
) {

    suspend fun execute(projectId: String, state: String): Boolean {
        val user = stateManager.getLoggedInUser()
        if (!user.isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidName(state)) throw InvalidStateNameException
        if (!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException

        val uuid = UUID.fromString(projectId)
        val project = repository.getProjectById(uuid)

        if (project.states.any { it.trim().equals(state.trim(), ignoreCase = true) }) {
            throw PlanMateException.ValidationException.SameStateNameException
        }

        return repository.addStateById(uuid, state)
    }
}