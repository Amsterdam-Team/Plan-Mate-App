package logic.repository

import logic.entities.Project
import java.util.*

interface ProjectRepository {
    fun createProject(project: Project): Boolean
    fun updateProjectNameById(projectId: UUID, newName: String): Boolean
    fun deleteProject(projectId: UUID): Boolean
    fun getProjects(): List<Project>
    fun getProject(projectId: UUID): Project

    fun updateProjectStateById(projectId: UUID, oldState: String, newState: String): Boolean
    fun deleteStateById(projectId: UUID, oldState: String): Boolean
    fun addStateById(projectId: UUID, state: String): Boolean

}