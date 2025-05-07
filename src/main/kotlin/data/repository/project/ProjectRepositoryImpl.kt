package data.repository.project

import data.datasources.projectDataSource.IProjectDataSource
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import java.util.*


class ProjectRepositoryImpl(private val projectDataSource: IProjectDataSource) : ProjectRepository {

    override fun createProject(project: Project) =
        projectDataSource.insertProject(project)


    override fun updateProjectNameById(projectId: UUID, newName: String) =
        projectDataSource.updateProjectName(projectId,newName)


    override fun deleteProject(projectId: UUID) = projectDataSource.deleteProject(projectId)


    override fun getProjects() =
        projectDataSource.getAllProjects()


    override fun getProject(projectId: UUID): Project {
        val project = try {
            projectDataSource.getProjectById(projectId)
        } catch (ex: PlanMateException.DataSourceException.ObjectDoesNotExistException) {
            throw ProjectNotFoundException
        }
        return project
    }

    override fun updateProjectStateById(projectId: UUID, oldState: String, newState: String) =
        projectDataSource.updateProjectState(projectId,oldState,newState)


    override fun deleteStateById(projectId: UUID, oldState: String)=
        projectDataSource.deleteProjectState(projectId,oldState)


    override fun addStateById(projectId: UUID, state: String) =
        projectDataSource.insertProjectState(projectId,state)

}