package org.amsterdam.planmate.data.datasources.project

import logic.entities.Project
import java.util.*

interface ProjectDataSource {
    fun addProject(projectName:String)
    fun deleteProjectByName(name:String)
    fun getProjectByName(name: String): Project
    fun updateProjectName(projectId: UUID,newName:String)
    fun getAllProjects(): List<Project>
}