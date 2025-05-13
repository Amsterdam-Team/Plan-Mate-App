package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import java.util.*

class CreateProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val stateManager: StateManager,
    private val validateInputUseCase: ValidateInputUseCase,
) {
    suspend fun createProject(name: String, states: List<String>): Boolean {

        if (!stateManager.getLoggedInUser().isAdmin) throw AdminPrivilegesRequiredException
        if (!validateInputUseCase.isValidName(name)) throw InvalidProjectNameException
        if (!validateInputUseCase.isValidProjectStates(states)) throw InvalidStateNameException
        val projectId = UUID.randomUUID()
        val createdProject = Project(id = projectId, name = name, states = states, tasks = emptyList())
        return projectRepository.createProject(createdProject)
    }
}