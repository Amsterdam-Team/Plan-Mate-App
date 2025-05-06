package logic.usecases.project

import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import java.util.UUID

class DeleteProjectUseCase(val projectRepository: ProjectRepository){

    fun deleteProject(projectId: String): Boolean {
        validateId(projectId)
        return projectRepository.deleteProject(UUID.fromString(projectId))
    }

    private fun validateId(id: String) {
        if(id.isEmpty() || id.isBlank()){
            throw EmptyDataException
        }
        try {
            UUID.fromString(id)
        }catch (e: IllegalArgumentException){
            throw InvalidProjectIDException
        }
    }

}