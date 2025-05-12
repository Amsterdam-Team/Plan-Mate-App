package logic.repository

import logic.entities.Project
import java.util.*

interface IProjectRepository {
    suspend fun createProject(project: Project): Boolean
    suspend fun updateProjectNameById(projectId: UUID, newName: String): Boolean
    suspend fun deleteProjectById(projectId: UUID): Boolean
    suspend fun getProjects(): List<Project>
    suspend fun getProjectById(projectId: UUID): Project

    suspend fun updateProjectStateById(projectId: UUID, oldState: String, newState: String): Boolean
    suspend fun deleteStateById(projectId: UUID, oldState: String): Boolean
    suspend fun addStateById(projectId: UUID, state: String): Boolean

}