package logic.usecases.state

import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.NotFoundException.StateNotFoundException
import logic.exception.PlanMateException.ValidationException.*
import logic.repository.IProjectRepository
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import java.util.*

class DeleteStateUseCase(
    private val repository: IProjectRepository,
    private val taskRepository: ITaskRepository,
    private val stateManager: StateManager,
    private val loggerUseCase: LoggerUseCase,
    private val validateInputUseCase: ValidateInputUseCase
) {
    suspend operator fun invoke(projectId: String, state: String): Boolean {
        val user = stateManager.getLoggedInUser()
        if (!user.isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidName(state)) throw InvalidStateNameException
        if (!validateInputUseCase.isValidUUID(projectId)) throw InvalidProjectIDException

        val uuid = UUID.fromString(projectId)

        val project = repository.getProjectById(uuid) ?: throw ProjectNotFoundException

        if (!project.states.contains(state)) {
            throw StateNotFoundException
        }

        val hasTasks = taskRepository.hasTasksWithState(uuid, state)
        if (hasTasks) {
            throw StateHasTasksException
        }

        val deleted = repository.deleteStateById(uuid, state)

        if (deleted) {
            loggerUseCase.createLog("$DELETE_STATE_KEYWORD ${state} $IN_PROJECT_NAME_KEYWORD ${project.name}", uuid)
        }

        return deleted
    }

    companion object{
        const val  DELETE_STATE_KEYWORD = "Delete State"
        const val  IN_PROJECT_NAME_KEYWORD = "in Project Name"
    }
}

