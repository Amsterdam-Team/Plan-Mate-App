package logic.usecases.project

import logic.entities.Project
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import java.util.UUID

class GetProjectUseCase(
    private val projectRepository: ProjectRepository
) {

    fun getProject(projectID: String): Project {
        val id = validateAndParseProjectID(projectID)
        val projects = projectRepository.getProjects()
        if (projects.isEmpty()) throw EmptyDataException
        return projects.find { it.id == id } ?: throw ProjectNotFoundException

    }

    private fun validateAndParseProjectID(projectID: String): UUID {
        return try {
            UUID.fromString(projectID)
        } catch (e: IllegalArgumentException) {
            throw InvalidProjectIDException
        }
    }
}