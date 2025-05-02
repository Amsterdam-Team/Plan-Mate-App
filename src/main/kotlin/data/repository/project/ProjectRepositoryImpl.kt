package data.repository.project

import data.datasources.DataSource
import kotlinx.datetime.LocalDateTime
import logic.entities.LogItem
import logic.entities.Project
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import java.time.LocalDateTime.now
import java.util.*

class ProjectRepositoryImpl(private val dataSource: DataSource, val logRepository: LogRepository ) : ProjectRepository {
    override fun createProject(project: Project) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "add project with name ${project.name}",
                date = now(),
                entityId = project.id,
            )
        )
    }

    override fun updateProjectNameById(id: UUID, newName: String) {

        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "update project name to be ${newName}",
                date = now(),
                entityId = id,
            )
        )
    }

    override fun deleteProject(projectId: UUID) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "deletle project with id ${projectId.toString()}",
                date = now(),
                entityId = projectId,
            )
        )

    }

    override fun getProjects(): List<Project> {
        return dataSource.getAll().map { it as Project }
    }

    override fun getProject(id: UUID): Project {
        return dataSource.getById(id) as Project
    }

    override fun updateProjectStateById(id: UUID, oldState: String, newState: String) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "update project state from ${oldState} to be ${newState}",
                date = now(),
                entityId = id,
            )
        )
    }

    override fun deleteStateById(id: UUID, oldState: String) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "delete project state with name ${oldState}",
                date = now(),
                entityId = id,
            )
        )
    }

    override fun addStateById(id: UUID, state: String) {
        logRepository.addLog(
            LogItem(
                id = UUID.randomUUID(),
                message = "add project state with name ${state}",
                date = now(),
                entityId = id,
            )
        )
    }

}