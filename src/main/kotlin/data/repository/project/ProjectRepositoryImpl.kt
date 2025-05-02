package data.repository.project

import data.datasources.DataSource
import logic.entities.Project
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import java.util.UUID

class ProjectRepositoryImpl(private val dataSource: DataSource) : ProjectRepository {
    override fun createProject(project: Project) {
        TODO("Not yet implemented")
    }

    override fun updateProjectNameById(id: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: UUID) {
        dataSource
        throw Exception("unimplemented yet")

    }

    override fun getProjects(): List<Project> {
        return dataSource.getAll().map { it as Project }
    }

    override fun getProject(id: UUID): Project {
        return dataSource.getById(id) as Project
    }

    override fun updateProjectStateById(id: UUID, oldState: String, newState: String) {
        val projects = dataSource.getAll().map { it as Project }
        val project = projects.find { it.id == id } ?: throw PlanMateException.NotFoundException.ProjectNotFoundException

        val updatedProject = project.copy(
            states = project.states.map {
                if (it == oldState) newState else it
            }
        )

        val updatedProjects = projects.map {
            if (it.id == id) updatedProject else it
        }
        dataSource.saveAll(updatedProjects)
    }

    override fun deleteStateById(id: UUID, oldState: String) {
        TODO("Not yet implemented")
    }

    override fun addStateById(id: UUID, state: String) {
        TODO("Not yet implemented")
    }

}