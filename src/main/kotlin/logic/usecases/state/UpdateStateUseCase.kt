package logic.usecases.state

import logic.repository.IProjectRepository
import java.util.UUID

import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase

class UpdateStateUseCase(
    private val projectRepository: IProjectRepository,
    private val stateManager: StateManager,
    private val validateInputUseCase: ValidateInputUseCase
) {
    suspend fun updateState(projectID: String, oldState: String, newState: String): Boolean {
        val validateInputs = validateInputs(projectID, oldState, newState)
        val user = stateManager.getLoggedInUser()
        if (validateInputs && user.isAdmin) {
            return projectRepository.updateProjectStateById(UUID.fromString(projectID), oldState, newState)
        } else {
            throw AdminPrivilegesRequiredException
        }
    }


    private fun validateInputs(projectID: String, oldState: String, newState: String): Boolean {
        when {
            !validateInputUseCase.isValidUUID(projectID) -> {
                throw InvalidProjectIDException
            }

            !validateInputUseCase.isValidName(oldState) -> {
                throw InvalidStateNameException
            }

            !validateInputUseCase.isValidName(newState) -> {
                throw InvalidStateNameException
            }

            validateInputUseCase.areIdentical(oldState, newState) -> {
                throw SameStateNameException
            }

        }
        return true
    }
}