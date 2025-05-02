package logic.usecases.project

import logic.entities.User
import logic.exception.PlanMateException
import logic.logging.LogEvent
import logic.logging.toLogItem
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import java.util.*

class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository
) {
    fun editProjectName(user: User, projectId: UUID, newName: String) {
        validateAdmin(user)
        validateProjectName(newName)

        val existingProject = projectRepository.getProject(projectId)

        projectRepository.updateProjectNameById(existingProject.id, newName)

        val logEvent = LogEvent.ProjectNameUpdated(
            projectId = projectId,
            oldName = existingProject.name,
            newName = newName
        )

        logRepository.addLog(logEvent.toLogItem(user.id))
    }

    private fun validateAdmin(user: User) {
        if (!user.isAdmin) throw PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
    }


    private fun validateProjectName(name: String) {
        if (name.isBlank()) throw PlanMateException.ValidationException.InvalidProjectNameException
    }
}
