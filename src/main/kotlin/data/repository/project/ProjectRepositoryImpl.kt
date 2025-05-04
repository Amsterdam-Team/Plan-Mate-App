package data.repository.project

import data.datasources.projectDataSource.ProjectDataSourceInterface
import logic.entities.Project
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl(private val projectDataSource: ProjectDataSourceInterface) : ProjectRepository {

    override fun createProject(project: Project) =
        projectDataSource.insertProject(project)


    override fun updateProjectNameById(projectId: UUID, newName: String) =
        projectDataSource.updateProjectName(projectId,newName)


    override fun deleteProject(projectId: UUID) = projectDataSource.deleteProject(projectId)


    override fun getProjects() =
        projectDataSource.getAllProjects()


    override fun getProject(projectId: UUID) =
        projectDataSource.getProjectById(projectId)

    override fun updateProjectStateById(projectId: UUID, oldState: String, newState: String) =
        projectDataSource.updateProjectState(projectId,oldState,newState)


    override fun deleteStateById(projectId: UUID, oldState: String)=
        projectDataSource.deleteProjectState(projectId,oldState)


    override fun addStateById(projectId: UUID, state: String) =
        projectDataSource.insertProjectState(projectId,state)

}