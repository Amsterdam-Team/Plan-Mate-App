package logic.usecases.state

import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.repository.ProjectRepository
import java.util.*

class DeleteStateUseCase(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(projectId: UUID, oldState: String): Boolean {
        validateStateName(oldState)

        val project = projectRepository.getProject(projectId) ?: throw ProjectNotFoundException

        return projectRepository.deleteStateById(project.id, oldState)
    }


    private fun validateStateName(state: String) {
        if (state.isBlank()) throw InvalidStateNameException
    }
}


