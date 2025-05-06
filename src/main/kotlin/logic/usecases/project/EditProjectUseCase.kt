package logic.usecases.project

import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import java.util.*
import logic.usecases.ValidateInputUseCase
import logic.entities.Project
import logic.entities.LogItem
import kotlinx.datetime.TimeZone


class EditProjectNameUseCase(
    private val projectRepository: ProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val logRepository: LogRepository
) {

    fun execute(user: User, projectId: UUID, newName: String) {
        validateAdmin(user)
        validateName(newName)
        val project = ensureProjectExists(projectId)
        validateProjectNameNotTaken(projectId, newName)
        updateProjectName(projectId, newName)
        logProjectNameChange(project, newName, user)
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

    private fun ensureProjectExists(projectId: UUID): Project {
        return projectRepository.getProject(projectId)
    }

    private fun updateProjectName(projectId: UUID, newName: String) {
      projectRepository.updateProjectNameById(projectId, newName)

    }

    private fun validateProjectNameNotTaken(projectId: UUID, newName: String) {
        val allProjects = projectRepository.getProjects()
        val nameExists = allProjects.any {
            it.id != projectId && it.name.equals(newName, ignoreCase = true)
        }
        if (nameExists) {
            throw PlanMateException.ValidationException.ProjectNameAlreadyExistException
        }
    }

    private fun logProjectNameChange(project: Project, newName: String, user: User) {
        val log = LogItem(
            id = UUID.randomUUID(),
            entityId = project.id,
            message = "Project name was changed from '${project.name}' to '$newName' by user ${user.id}",
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
        logRepository.addLog(log)
    }
}
