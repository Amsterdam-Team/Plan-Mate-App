package logic.repository

import logic.entities.Project
import java.util.UUID

interface ProjectRepository {
    fun createProject(project: Project)
    fun updateProjectNameById(id : UUID, newName :String)
    fun deleteProject(projectId: String)
    fun getProjects(): List<Project>
    fun getProject(id : UUID) : Project

    fun updateProjectStateById(id : UUID, oldState :String ,newState :String)
    fun deleteStateById(id : UUID, oldState :String)
    fun addStateById(id : UUID, state :String)

}