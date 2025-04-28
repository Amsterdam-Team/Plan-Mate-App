package org.amsterdam.planmate.data.repository.project

import logic.entities.Project
import logic.repository.ProjectRepository
import org.amsterdam.planmate.data.datasources.project.ProjectDataSource
import java.util.*

class ProjectRepositoryImpl(val projectDataSource: ProjectDataSource): ProjectRepository {
    override fun addProject(name: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProjectByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun updateProjectName(projectId: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun getProjectByName(name: String): Project {
        TODO("Not yet implemented")
    }


    override fun getAllProjects(): List<Project> {
        TODO("Not yet implemented")
    }


}