package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.IProjectRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import java.util.*

class CreateProjectUseCase(
    private val projectRepository: IProjectRepository,
    private val stateManager: StateManager,
    private val validateInputUseCase: ValidateInputUseCase,
    private val loggerUseCase: LoggerUseCase
) {
    suspend fun createProject(name: String, states: List<String>): Boolean {

        if (!stateManager.getLoggedInUser().isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidName(name)) throw InvalidProjectNameException
        if (!validateInputUseCase.isValidProjectStates(states)) throw InvalidStateNameException
        val projectId = UUID.randomUUID()
        val createdProject = Project(id = projectId, name = name, states = states, tasks = emptyList())
        return projectRepository.createProject(createdProject).also { isCreated ->
            if (isCreated) loggerUseCase.createLog("$CREATED_KEYWORD $name $PROJECT_KEYWORD", projectId)
        }
    }

    companion object{
        const val  CREATED_KEYWORD = "Created"
        const val  PROJECT_KEYWORD = "Project"
    }
}