package data.repository.project

import data.datasources.projectDataSource.ProjectDataSourceInterface
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.ProjectNameAlreadyExistException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl(
    private val projectDataSource: ProjectDataSourceInterface,
    private val logRepository: LogRepository,
) : ProjectRepository {
  
    override fun createProject(project: Project): Boolean {
        val existedProjects = getProjects()
        if (existedProjects.any { it.name.equals(project.name, ignoreCase = true) }) {
            throw ProjectNameAlreadyExistException
        }
        projectDataSource.insertProject(project)
        logRepository.addLog(
            log = LogItem(
                id = UUID.randomUUID(),
                message = "This project is created at that time",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                entityId = project.id
            )
        )
        return true
    }


    override fun updateProjectNameById(projectId: UUID, newName: String) =
        projectDataSource.updateProjectName(projectId, newName)


    override fun deleteProject(projectId: UUID) = projectDataSource.deleteProject(projectId)


    override fun getProjects() = projectDataSource.getAllProjects()



    override fun getProject(projectId: UUID): Project {
        val project = try {
            projectDataSource.getProjectById(projectId)
        } catch (ex: PlanMateException.DataSourceException.ObjectDoesNotExistException) {
            throw ProjectNotFoundException
        }
        return project
    }

    override fun updateProjectStateById(projectId: UUID, oldState: String, newState: String) =
        projectDataSource.updateProjectState(projectId, oldState, newState)


    override fun deleteStateById(projectId: UUID, oldState: String) =
        projectDataSource.deleteProjectState(projectId, oldState)


    override fun addStateById(projectId: UUID, state: String) = projectDataSource.insertProjectState(projectId, state)

}