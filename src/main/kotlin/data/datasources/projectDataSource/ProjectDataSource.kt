package data.datasources.projectDataSource

import com.mongodb.kotlin.client.coroutine.MongoCollection
import logic.entities.Project
import java.util.*

class ProjectDataSource(
    projectCollection: MongoCollection<Project>
): IProjectDataSource {
    override suspend fun getAllProjects(): List<Project> {
        TODO("Not yet implemented")
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        TODO("Not yet implemented")
    }

    override suspend fun insertProject(project: Project): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProject(projectId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateProjectName(projectId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun replaceAllProjects(projects: List<Project>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun insertProjectState(projectId: UUID, state: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getProjectStates(projectId: UUID): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProjectState(projectId: UUID, state: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean {
        TODO("Not yet implemented")
    }
}