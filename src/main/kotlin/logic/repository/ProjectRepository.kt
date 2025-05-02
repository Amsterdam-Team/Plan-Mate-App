package logic.repository

import logic.entities.Project
import java.util.*

interface ProjectRepository {
    fun createProject(project: Project)
    fun updateProjectNameById(id: UUID, newName: String)
    fun deleteProject(projectId: UUID)
    fun getProjects(): List<Project>
    fun getProject(id: UUID): Project

    fun updateProjectStateById(id: UUID, oldState: String, newState: String)
    fun deleteStateById(projectId: UUID, oldState: String)
    fun addStateById(id: UUID, state: String)

}