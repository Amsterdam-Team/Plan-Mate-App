package data.repository.project

import data.datasources.DataSource
import logic.entities.Project
import logic.exception.PlanMateException.ValidationException.ProjectNameAlreadyExistException
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl(val dataSource: DataSource) : ProjectRepository {
    override fun createProject(project: Project) {
        val existedProjects = dataSource.getAll().map { it as Project }
        if (existedProjects.any { it.name.equals(project.name, ignoreCase = true) }) {
            throw ProjectNameAlreadyExistException
        }
    }

    override fun updateProjectNameById(id: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: UUID) {
        dataSource
        throw Exception("unimplemented yet")

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

    override fun deleteStateById(id: UUID, oldState: UUID?) {
        TODO("Not yet implemented")
    }

    override fun addStateById(id: UUID, state: String) {
        TODO("Not yet implemented")
    }

}