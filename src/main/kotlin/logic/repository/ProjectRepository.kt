package logic.repository

import logic.entities.Project
import java.util.*

interface ProjectRepository {
    fun addProject(name: String)
    fun deleteProjectByName(name: String)
    fun getProjectByName(name: String): Project
    fun updateProjectName(projectId: UUID, newName: String)
    fun getAllProjects(): List<Project>
}
