package data.proxy

import data.datasources.mongoUtils.MongoTransactionManager
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import logic.entities.Project
import logic.entities.User
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.usecases.utils.StateManager
import java.util.*

class ProjectRepositoryProxy(
    private val delegate: ProjectRepository,
    private val logRepository: LogRepository,
    private val transactionManager: MongoTransactionManager
) : ProjectRepository {

    private val currentUser: User = StateManager.getLoggedInUser()

    override suspend fun createProject(project: Project): Boolean {
        return transactionManager.executeInTransaction {
            val result = delegate.createProject(project)
            if (result) {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                logRepository.addLog(
                    LogItem(
                        id = UUID.randomUUID(),
                        entityId = project.id,
                        date = now,
                        message = "${currentUser.username} created project '${project.name}' at ${formatTimeForDisplay(now)}"
                    )
                )
            }
            result
        }
    }

    override suspend fun updateProjectNameById(projectId: UUID, newName: String): Boolean {
        return transactionManager.executeInTransaction {
            val oldProject = delegate.getProject(projectId)
            val result = delegate.updateProjectNameById(projectId, newName)
            if (result) {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                logRepository.addLog(
                    LogItem(
                        id = UUID.randomUUID(),
                        entityId = projectId,
                        date = now,
                        message = "${currentUser.username} updated project name from '${oldProject.name}' to '$newName' at ${formatTimeForDisplay(now)}"
                    )
                )
            }
            result
        }
    }

    override suspend fun deleteProject(projectId: UUID): Boolean {
        return transactionManager.executeInTransaction {
            val projectToDelete = delegate.getProject(projectId)
            val result = delegate.deleteProject(projectId)
            if (result) {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                logRepository.addLog(
                    LogItem(
                        id = UUID.randomUUID(),
                        entityId = projectId,
                        date = now,
                        message = "${currentUser.username} deleted project '${projectToDelete.name}' at ${formatTimeForDisplay(now)}"
                    )
                )
            }
            result
        }
    }

    override suspend fun getProjects(): List<Project> {
        return delegate.getProjects()
    }

    override suspend fun getProject(projectId: UUID): Project {
        return delegate.getProject(projectId)
    }

    override suspend fun updateProjectStateById(projectId: UUID, oldState: String, newState: String): Boolean {
        return transactionManager.executeInTransaction {
            val result = delegate.updateProjectStateById(projectId, oldState, newState)
            if (result) {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                logRepository.addLog(
                    LogItem(
                        id = UUID.randomUUID(),
                        entityId = projectId,
                        date = now,
                        message = "${currentUser.username} updated project state from '$oldState' to '$newState' at ${formatTimeForDisplay(now)}"
                    )
                )
            }
            result
        }
    }

    override suspend fun deleteStateById(projectId: UUID, oldState: String): Boolean {
        return transactionManager.executeInTransaction {
            val result = delegate.deleteStateById(projectId, oldState)
            if (result) {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                logRepository.addLog(
                    LogItem(
                        id = UUID.randomUUID(),
                        entityId = projectId,
                        date = now,
                        message = "${currentUser.username} deleted state '$oldState' from project at ${formatTimeForDisplay(now)}"
                    )
                )
            }
            result
        }
    }

    override suspend fun addStateById(projectId: UUID, state: String): Boolean {
        return transactionManager.executeInTransaction {
            val result = delegate.addStateById(projectId, state)
            if (result) {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                logRepository.addLog(
                    LogItem(
                        id = UUID.randomUUID(),
                        entityId = projectId,
                        date = now,
                        message = "${currentUser.username} added state '$state' to project at ${formatTimeForDisplay(now)}"
                    )
                )
            }
            result
        }
    }

    companion object {
        fun formatTimeForDisplay(dateTime: kotlinx.datetime.LocalDateTime): String {
            val hour = when {
                dateTime.hour == 0 -> 12
                dateTime.hour > 12 -> dateTime.hour - 12
                else -> dateTime.hour
            }
            val minute = dateTime.minute.toString().padStart(2, '0')
            val amPm = if (dateTime.hour >= 12) "PM" else "AM"
            return "$hour:$minute $amPm"
        }
    }
}