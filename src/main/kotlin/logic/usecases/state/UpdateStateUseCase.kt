package logic.usecases.state

import logic.entities.User
import logic.repository.ProjectRepository
import java.util.UUID

import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.usecases.ValidateInputUseCase

class UpdateStateUseCase(
    private val projectRepository: ProjectRepository,
    private val user: User,
    private val validateInputUseCase: ValidateInputUseCase
) {
    fun updateState(projectID: String, oldState: String, newState: String): Boolean {
        val validateInputs = validateInputs(projectID, oldState, newState)
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