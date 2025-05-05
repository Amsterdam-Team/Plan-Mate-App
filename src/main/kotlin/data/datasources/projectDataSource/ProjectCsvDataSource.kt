package data.datasources.projectDataSource

import data.datasources.FileManager
import data.datasources.parser.CsvParser
import logic.entities.Project
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
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

    override fun getProjectById(projectId: UUID) = getAllProjects().find { it.id == projectId } ?: throw ProjectNotFoundException


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
        val projects = getAllProjects()
        val newProjects = projects.filterNot { it.id == projectId }
        if (projects == newProjects) return false
        return try {
            fileManager.writeLines(newProjects.map { csvParser.serialize(it) })
            true
        } catch (e: IOException){
            false
        }
    }

    override fun updateProjectName(projectId: UUID, newName: String): Boolean {
        val projects = getAllProjects()
        val oldProject = projects.find { it.id == projectId } ?: return false
        val newProjects = projects.filterNot { it.id == projectId }.toMutableList()
        val newProject = oldProject.copy(name = newName)
        newProjects.add(newProject)
        return try {
            fileManager.writeLines(newProjects.map { csvParser.serialize(it) })
            true
        } catch (e: IOException){
            false
        }
    }

    override fun replaceAllProjects(projects: List<Project>): Boolean {
        return try {
            fileManager.writeLines(projects.map { csvParser.serialize(it) })
            true
        } catch (e: EmptyDataException){
            return false
        } catch (e: IOException){
            return false
        }
    }

    override fun insertProjectState(projectId: UUID, state: String): Boolean {
        val projects = getAllProjects()
        val oldProject = projects.find { it.id == projectId } ?: return false
        val newProjects = projects.filterNot { it.id == projectId }.toMutableList()
        val newStates = oldProject.states.toMutableList()
        if (newStates.contains(state)) return false
        newStates.add(state)
        val newProject = oldProject.copy(states = newStates)
        newProjects.add(newProject)
        return try {
            fileManager.writeLines(newProjects.map { csvParser.serialize(it) })
            true
        } catch (e: IOException){
            false
        }
    }

    override fun getProjectStates(projectId: UUID) = getAllProjects().find { it.id == projectId }?.states ?: emptyList()


    override fun deleteProjectState(projectId: UUID, state: String): Boolean {
        val projects = getAllProjects()
        val oldProject = projects.find { it.id == projectId } ?: return false
        if (state !in oldProject.states) return false
        val newProjects = projects.filterNot { it.id == projectId }.toMutableList()
        val newStates = oldProject.states.toMutableList()
        newStates.remove(state)
        val newProject = oldProject.copy(states = newStates)
        newProjects.add(newProject)

        return try {
            fileManager.writeLines(newProjects.map { csvParser.serialize(it) })
            true
        } catch (e: IOException) {
            false
        }
    }

    override fun updateProjectState(projectId: UUID, oldState: String, newState: String): Boolean {
        val projects = getAllProjects()
        val oldProject = projects.find { it.id == projectId } ?: return false
        if (oldState !in oldProject.states) return false
        val newProjects = projects.filterNot { it.id == projectId }.toMutableList()
        val newStates = oldProject.states.toMutableList()
        newStates.remove(oldState)
        newStates.add(newState)
        val newProject = oldProject.copy(states = newStates)
        newProjects.add(newProject)

        return try {
            fileManager.writeLines(newProjects.map { csvParser.serialize(it) })
            true
        } catch (e: IOException) {
            false
        }
    }
}