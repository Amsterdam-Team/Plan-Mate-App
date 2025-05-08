package logic.usecases.project

import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import java.util.*
import logic.usecases.utils.ValidateInputUseCase
import logic.entities.Project
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager


class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val stateManager: StateManager,
    private val loggerUseCase: LoggerUseCase
    )  {

    suspend fun editProjectName( projectId: UUID, newName: String) : Boolean {
        val user = stateManager.getLoggedInUser()
        validateAdmin(user)
        validateName(newName)
        val project = ensureProjectExists(projectId)
        validateProjectNameNotTaken(projectId, newName)

        return projectRepository.updateProjectNameById(projectId, newName).also { isEdited ->
            if (isEdited) loggerUseCase.createLog("Edited project ${project.name} name to $newName", projectId)
        }
    }

    private fun validateAdmin(user: User) {
        require(user.isAdmin) {
            throw PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
        }
    }

    private fun validateName(name: String) {
        require(validateInputUseCase.isValidName(name)) {
            throw PlanMateException.ValidationException.InvalidProjectNameException
        }
    }

    private suspend fun ensureProjectExists(projectId: UUID): Project {
        return projectRepository.getProject(projectId)
    }

    private suspend  fun validateProjectNameNotTaken(projectId: UUID, newName: String) {
        val allProjects = projectRepository.getProjects()
        val nameExists = allProjects.any {
            it.id != projectId && it.name.equals(newName, ignoreCase = true)
        }
        if (nameExists) {
            throw PlanMateException.ValidationException.ProjectNameAlreadyExistException
        }
    }
}
