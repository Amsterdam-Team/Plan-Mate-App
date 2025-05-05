package data.datasources.projectDataSource

import logic.entities.Project
import java.util.*

class ProjectCsvDataSource: ProjectDataSourceInterface {
    override fun getAllProjects(): List<Project> {
        TODO("Not yet implemented")
    }

    override fun getProjectById(projectId: UUID): Project {
        TODO("Not yet implemented")
    }

    override fun insertProject(project: Project): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateProjectName(projectId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun replaceAllProjects(projects: List<Project>): Boolean {
        TODO("Not yet implemented")
    }

    override fun insertProjectState(projectId: UUID, state: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getProjectStates(projectId: UUID): List<String> {
        TODO("Not yet implemented")
    }

    override fun deleteProjectState(projectId: UUID, state: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean {
        TODO("Not yet implemented")
    }
}