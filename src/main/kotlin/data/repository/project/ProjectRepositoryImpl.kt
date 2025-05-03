package data.repository.project

import data.datasources.DataSource
import data.datasources.projectDataSource.ProjectDataSourceInterface
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.ProjectNameAlreadyExistException
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl(private val projectDataSourceInterface: ProjectDataSourceInterface) : ProjectRepository {

    override fun createProject(project: Project) =
        projectDataSourceInterface.insertProject(project)


    override fun updateProjectNameById(projectId: UUID, newName: String) =
        projectDataSourceInterface.updateProjectName(projectId,newName)


    override fun deleteProject(projectId: UUID) =
        projectDataSourceInterface.deleteProject(projectId)


    override fun getProjects() =
        projectDataSourceInterface.getAllProjects()


    override fun getProject(projectId: UUID) =
        projectDataSourceInterface.getProjectById(projectId)

    override fun updateProjectStateById(projectId: UUID, oldState: String, newState: String) =
        projectDataSourceInterface.updateProjectState(projectId,oldState,newState)


    override fun deleteStateById(projectId: UUID, oldState: String)=
        projectDataSourceInterface.deleteProjectState(projectId,oldState)


    override fun addStateById(projectId: UUID, state: String) =
        projectDataSourceInterface.insertProjectState(projectId,state)

}