package data.repository.project

import logic.entities.Project
import logic.repository.ProjectRepository
import java.util.*

class ProjectRepositoryImpl: ProjectRepository {
    override fun createProject(project: Project) {
        TODO("Not yet implemented")
    }

    override fun updateProjectNameById(id: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: String) {
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