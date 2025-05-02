package data.repository.project

import data.datasources.CsvDataSource
import data.datasources.DataSource
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl(val dataSource: DataSource) : ProjectRepository {
    override fun createProject(project: Project) {
        TODO("Not yet implemented")
    }

    override fun updateProjectNameById(id: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: UUID) {
        val allProject= dataSource.getAll().map { it as Project }
        if (allProject.isEmpty()) throw EmptyDataException
        if(allProject.find { it.id== projectId } == null) throw ProjectNotFoundException
        try {
            val newProjectsList = allProject.filterNot { it.id == projectId }
            dataSource.saveAll(newProjectsList)
        }catch (e: Exception){
            throw Exception("Error Saving projects")
        }
    }

    override fun getProjects(): List<Project> {
        TODO("Not yet implemented")
    }

    override fun getProject(id: UUID): Project {
        TODO("Not yet implemented")
    }

    override fun updateProjectStateById(id: UUID, oldState: String, newState: String) {
        TODO("Not yet implemented")
    }

    override fun deleteStateById(id: UUID, oldState: String) {
        TODO("Not yet implemented")
    }

    override fun addStateById(id: UUID, state: String) {
        TODO("Not yet implemented")
    }

}