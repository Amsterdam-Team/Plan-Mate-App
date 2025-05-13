package data.repository.project

import data.datasources.projectDataSource.IProjectDataSource
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.ProjectNameAlreadyExistException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.IProjectRepository
import java.util.*

class ProjectRepository(
    private val projectDataSource: IProjectDataSource,
) : IProjectRepository {
  
    override suspend fun createProject(project: Project): Boolean {
        val existedProjects = getProjects()
        if (existedProjects.any { it.name.equals(project.name, ignoreCase = true) }) {
            throw ProjectNameAlreadyExistException
        }
        projectDataSource.insertProject(project)
        return true
    }

    override suspend fun updateProjectNameById(projectId: UUID, newName: String) =
        projectDataSource.updateProjectName(projectId, newName)


    override suspend fun deleteProjectById(projectId: UUID) = projectDataSource.deleteProjectById(projectId)

    override suspend fun getProjects() = projectDataSource.getAllProjects()

    override suspend fun getProjectById(projectId: UUID): Project {
        val project = try {
            projectDataSource.getProjectById(projectId)
        } catch (ex: PlanMateException.DataSourceException.ObjectDoesNotExistException) {
            throw ProjectNotFoundException
        }
        return project
    }

    override suspend fun updateProjectStateById(projectId: UUID, oldState: String, newState: String) =
        projectDataSource.updateProjectStateById(projectId, oldState, newState)


    override suspend fun deleteStateById(projectId: UUID, oldState: String) =
        projectDataSource.deleteProjectStateById(projectId, oldState)

    override suspend fun addStateById(projectId: UUID, state: String) = projectDataSource.insertProjectState(projectId, state)

}