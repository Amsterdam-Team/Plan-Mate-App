package data.repository.project

import data.datasources.DataSource
import logic.entities.Project
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.ProjectNameAlreadyExistException
import logic.repository.ProjectRepository
import java.util.UUID

class ProjectRepositoryImpl(val dataSource: DataSource) : ProjectRepository {
    override fun createProject(project: Project) {
        val existedProjects = dataSource.getAll().map { it as Project }
        if (existedProjects.any { it.name.equals(project.name, ignoreCase = true) }) {
            throw ProjectNameAlreadyExistException
        }
        dataSource.add(project)
    }

    override fun updateProjectNameById(id: UUID, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteProject(projectId: UUID) {
        val allProject = dataSource.getAll().map { it as Project }
        if (allProject.isEmpty()) throw EmptyDataException
        allProject.find { it.id == projectId } ?: throw ProjectNotFoundException
        val newProjectsList = allProject.filterNot { it.id == projectId }
        dataSource.saveAll(newProjectsList)

    }

    override fun getProjects(): List<Project> {
        return dataSource.getAll().map { it as Project }
    }

    override fun getProject(id: UUID): Project {
        return dataSource.getById(id) as Project
    }

    override fun updateProjectStateById(id: UUID, oldState: String, newState: String) {
        val projects = dataSource.getAll().map { it as Project }
        val project =
            projects.find { it.id == id } ?: throw ProjectNotFoundException

        val updatedProject = project.copy(
            states = project.states.map {
                if (it == oldState) newState else it
            }
        )

        val updatedProjects = projects.map {
            if (it.id == id) updatedProject else it
        }
        dataSource.saveAll(updatedProjects)
    }

    override fun deleteStateById(projectId: UUID, oldState: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun addStateById(projectId: UUID, state: String) {
        val allProjects = dataSource.getAll().map { it as Project }.toMutableList()

        val projectIndex = allProjects.indexOfFirst { it.id == projectId }

        if (projectIndex == -1) throw ProjectNotFoundException

        val targetProject = allProjects[projectIndex]
        val updatedProject = targetProject.copy(states = targetProject.states + state)

        allProjects[projectIndex] = updatedProject

        dataSource.saveAll(allProjects)
    }
}