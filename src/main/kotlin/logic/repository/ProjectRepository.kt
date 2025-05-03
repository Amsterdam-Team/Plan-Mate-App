package logic.repository

import logic.entities.Project
import java.util.*

interface ProjectRepository {
    fun createProject(project: Project): Boolean
    fun updateProjectNameById(id: UUID, newName: String): Boolean
    fun deleteProject(projectId: UUID): Boolean
    fun getProjects(): List<Project>
    fun getProject(id: UUID): Project

    fun updateProjectStateById(id: UUID, oldState: String, newState: String): Boolean
    fun deleteStateById(projectId: UUID, oldState: String): Boolean
    fun addStateById(id: UUID, state: String): Boolean

}