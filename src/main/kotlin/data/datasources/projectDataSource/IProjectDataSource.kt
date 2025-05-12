package data.datasources.projectDataSource

import logic.entities.Project
import java.util.UUID

interface IProjectDataSource {
    suspend fun getAllProjects(): List<Project>

    suspend fun getProjectById(projectId: UUID): Project

    suspend fun insertProject(project: Project): Boolean

    suspend fun deleteProjectById(projectId: UUID): Boolean


    suspend fun updateProjectName(projectId: UUID, newName: String): Boolean

    suspend fun insertProjectState(projectId: UUID, state: String): Boolean

    suspend fun getProjectStatesById(projectId: UUID): List<String>

    suspend fun deleteProjectStateById(projectId: UUID, state: String): Boolean

    suspend fun updateProjectStateById(projectId: UUID, oldState: String, newState: String): Boolean
}