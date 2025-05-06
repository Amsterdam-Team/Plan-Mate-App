package data.datasources.projectDataSource

import logic.entities.Project
import java.util.UUID

interface ProjectDataSourceInterface {
    suspend fun getAllProjects(): List<Project>

    suspend fun getProjectById(projectId: UUID): Project

    suspend fun insertProject(project: Project): Boolean

    suspend fun deleteProject(projectId: UUID): Boolean


    suspend fun updateProjectName(projectId: UUID, newName: String): Boolean

    suspend fun replaceAllProjects(projects: List<Project>): Boolean

    suspend fun insertProjectState(projectId: UUID, state: String): Boolean

    suspend fun getProjectStates(projectId: UUID): List<String>

    suspend fun deleteProjectState(projectId: UUID, state: String): Boolean

    suspend fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean
}