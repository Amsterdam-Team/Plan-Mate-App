package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.UUID
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.LogRepository

class UpdateStateUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository
) {
    fun updateState(projectID:String,oldState:String,newState:String){
        val id = validateAndParseProjectID(projectID)
        validateInputs(oldState,newState)
        projectRepository.getProjects()
            .ifEmpty { throw EmptyDataException }
            .find { it.id == id } ?: throw ProjectNotFoundException

        projectRepository.updateProjectStateById(id, oldState, newState)
        logRepository.addLog(
            "update project state from ${oldState} to $newState}",
            id
        )
    }

    private fun validateAndParseProjectID(projectID: String): UUID {
        return try {
            UUID.fromString(projectID)
        } catch (e: IllegalArgumentException) {
            throw InvalidProjectIDException
        }
    }
    private fun validateInputs(oldState: String, newState: String) {
        if (oldState.isBlank() || newState.isBlank()) throw InvalidStateNameException
        if (oldState == newState) throw SameStateNameException

    }
}