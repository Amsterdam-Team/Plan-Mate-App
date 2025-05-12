package data.datasources.projectDataSource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.entities.Project
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.ObjectDoesNotExistException
import java.util.*

class ProjectDataSource(
    private val projectCollection: MongoCollection<Project>
) : IProjectDataSource {
    override suspend fun getAllProjects(): List<Project> {
        return projectCollection.find().toList()
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        return projectCollection.find(Filters.eq(FIELD_ID, projectId)).firstOrNull()
            ?: throw ObjectDoesNotExistException
    }

    override suspend fun insertProject(project: Project): Boolean {
        val updateResult = projectCollection.updateOne(
            Filters.or(
                Filters.eq(FIELD_ID, project.id),
                Filters.eq(FIELD_NAME, project.name)
            ),
            Updates.combine(
                Updates.setOnInsert(FIELD_NAME, project.name),
                Updates.setOnInsert(FIELD_TASKS, emptyList<Task>()),
                Updates.addEachToSet(FIELD_STATES, project.states)
            ),
            UpdateOptions().upsert(true)
        )

        return updateResult.upsertedId != null || updateResult.modifiedCount > 0
    }

    override suspend fun deleteProject(projectId: UUID): Boolean {
        val result = projectCollection.deleteOne(Filters.eq(FIELD_ID, projectId))
        return result.deletedCount > 0
    }

    override suspend fun updateProjectName(projectId: UUID, newName: String): Boolean {
        val result = projectCollection.updateOne(
            Filters.eq(FIELD_ID, projectId),
            Updates.set(FIELD_NAME, newName)
        )
        return result.modifiedCount > 0
    }

    override suspend fun insertProjectState(projectId: UUID, state: String): Boolean {
        val result = projectCollection.updateOne(
            Filters.eq(FIELD_ID, projectId),
            Updates.addToSet(FIELD_STATES, state)
        )
        return result.modifiedCount > 0
    }

    override suspend fun getProjectStates(projectId: UUID): List<String> {
        val project =
            projectCollection.find(Filters.eq(FIELD_ID, projectId)).firstOrNull()
                ?: return emptyList()

        return project.states
    }

    override suspend fun deleteProjectState(projectId: UUID, state: String): Boolean {
        val result =
            projectCollection.updateOne(
                Filters.eq(FIELD_ID, projectId),
                Updates.pull(FIELD_STATES, state)
            )

        return result.modifiedCount > 0
    }

    override suspend fun updateProjectState(
        projectId: UUID,
        oldState: String,
        newState: String
    ): Boolean {
        val idFilter = Filters.eq(FIELD_ID, projectId)
        val removeResult =
            projectCollection.updateOne(idFilter, Updates.pull(FIELD_STATES, oldState))
        if (removeResult.modifiedCount.toInt() == 0) return false

        val addResult =
            projectCollection.updateOne(idFilter, Updates.addToSet(FIELD_STATES, newState))

        return addResult.modifiedCount > 0
    }

    private companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_STATES = "states"
        const val FIELD_TASKS = "tasks"
    }
}