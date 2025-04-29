package data.repository.project

import data.datasources.DataSource
import logic.entities.Project
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl(val dataSource: DataSource<Project>): ProjectRepository {
    override fun createProject(project: Project) {
        dataSource
        throw Exception("unimplemented yet")
    }

    override fun updateProjectNameById(id: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: UUID) {
        TODO("Not yet implemented")
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