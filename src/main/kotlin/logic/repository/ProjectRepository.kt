package logic.repository

import logic.entities.Project
import java.util.*

interface ProjectRepository {
    suspend fun createProject(project: Project): Boolean
    suspend fun updateProjectNameById(projectId: UUID, newName: String): Boolean
    suspend fun deleteProject(projectId: UUID): Boolean
    suspend fun getProjects(): List<Project>
    suspend fun getProject(projectId: UUID): Project

    suspend fun updateProjectStateById(projectId: UUID, oldState: String, newState: String): Boolean
    suspend fun deleteStateById(projectId: UUID, oldState: String): Boolean
    suspend fun addStateById(projectId: UUID, state: String): Boolean

}