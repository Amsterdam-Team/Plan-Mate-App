package data.datasources.projectDataSource

import logic.entities.Project
import java.util.UUID

interface ProjectDataSourceInterface {
    fun getAllProjects(): List<Project>

    fun getProjectById(projectId: UUID): Project

    fun insertProject(project: Project): Boolean

    fun deleteProject(projectId: UUID): Boolean


    fun updateProjectName(projectId: UUID, newName: String): Boolean

    fun replaceAllProjects(projects: List<Project>): Boolean

    fun insertProjectState(projectId: UUID, state: String): Boolean

    fun getProjectStates(projectId: UUID): List<String>

    fun deleteProjectState(projectId: UUID, state: String): Boolean

    fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean
}