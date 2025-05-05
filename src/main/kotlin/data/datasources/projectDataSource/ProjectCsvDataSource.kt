package data.datasources.projectDataSource

import data.datasources.FileManager
import data.datasources.parser.CsvParser
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyFileException
import java.io.IOException
import java.util.*

class ProjectCsvDataSource (
    private val fileManager: FileManager<Project>,
    private val csvParser: CsvParser<Project>
): ProjectDataSourceInterface {
    override fun getAllProjects(): List<Project> {
        return try {
            val lines = fileManager.readLines()
            lines.map { csvParser.deserialize(it) }
        } catch (e: EmptyFileException){
            emptyList()
        }
    }

    override fun getProjectById(projectId: UUID): Project {
        TODO("Not yet implemented")
    }

    override fun insertProject(project: Project): Boolean {
        val projects = getAllProjects()
        val exists = projects.find { project.id == it.id }
        if (exists != null){
            return false
        }
        val line = csvParser.serialize(project)
        return try {
            fileManager.appendLine(line)
            true
        } catch (e: IOException){
            false
        }
    }

    override fun deleteProject(projectId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateProjectName(projectId: UUID, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun replaceAllProjects(projects: List<Project>): Boolean {
        TODO("Not yet implemented")
    }

    override fun insertProjectState(projectId: UUID, state: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getProjectStates(projectId: UUID): List<String> {
        TODO("Not yet implemented")
    }

    override fun deleteProjectState(projectId: UUID, state: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean {
        TODO("Not yet implemented")
    }
}