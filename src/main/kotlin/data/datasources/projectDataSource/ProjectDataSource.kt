package data.datasources.projectDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.Project
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import org.bson.Document
import java.util.*

class ProjectDataSource(
    private val projectCollection: MongoCollection<Project>
): IProjectDataSource {
    override suspend fun getAllProjects(): List<Project> {
        return projectCollection.find().toList()
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        return projectCollection.find(Filters.eq("id", projectId)).firstOrNull() ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertProject(project: Project): Boolean {
        val existingUser = projectCollection.find(
            Filters.or(
                Filters.eq("id", project.id),
                Filters.eq("name", project.name)
            )
        ).firstOrNull()

        if (existingUser != null) return false

        return projectCollection.insertOne(project.copy(tasks = emptyList())).wasAcknowledged()
    }

    override suspend fun deleteProject(projectId: UUID): Boolean {
        val result = projectCollection.deleteOne(Filters.eq("id", projectId))
        return result.deletedCount > 0
    }

    override suspend fun updateProjectName(projectId: UUID, newName: String): Boolean {
        val result = projectCollection.updateOne(
            Filters.eq("id", projectId),
            Updates.set("name", newName)
        )
        return result.modifiedCount > 0
    }

    override suspend fun replaceAllProjects(projects: List<Project>): Boolean {
        val hasDuplicateId = projects.map { it.id }.toSet().size != projects.size
        val hasDuplicateNames = projects.map { it.name }.toSet().size != projects.size

        if (hasDuplicateId || hasDuplicateNames) return false

        projectCollection.deleteMany(Document())
        val result = projectCollection.insertMany(projects.map { it.copy(tasks = emptyList()) })
        return result.wasAcknowledged() && result.insertedIds.size == projects.size
    }

    override suspend fun insertProjectState(projectId: UUID, state: String): Boolean {
        val project = projectCollection.find(Filters.eq("id", projectId)).firstOrNull() ?: return false

        if (state in project.states) return false

        val updatedStates = project.states.toMutableList()
        updatedStates.add(state)

        val result = projectCollection.updateOne(
            Filters.eq("id", projectId),
            Updates.set("states", updatedStates)
        )

        return result.modifiedCount > 0
    }

    override suspend fun getProjectStates(projectId: UUID): List<String> {
        val project = projectCollection.find(Filters.eq("id", projectId)).firstOrNull() ?: return emptyList()

        return project.states
    }

    override suspend fun deleteProjectState(projectId: UUID, state: String): Boolean {
        val project = projectCollection.find(Filters.eq("id", projectId)).firstOrNull() ?: return false

        if (state !in project.states) return false

        val updatedStates = project.states.filterNot { state == it }

        val result = projectCollection.updateOne(
            Filters.eq("id", projectId),
            Updates.set("states", updatedStates)
        )

        return result.modifiedCount > 0
    }

    override suspend fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean {
        val project = projectCollection.find(Filters.eq("id", projectId)).firstOrNull() ?: return false

        if (oldState !in project.states) return false

        if (newState in project.states) return false

        val updatedStates = project.states.map { if (it == oldState) newState else it }

        val result = projectCollection.updateOne(
            Filters.eq("id", projectId),
            Updates.set("states", updatedStates)
        )

        return result.modifiedCount > 0
    }
}