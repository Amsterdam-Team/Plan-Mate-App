package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.UUID

class GetProjectStatesUseCase(private val repository: ProjectRepository) {

    fun getProjectStatesByProjectID(id: UUID): List<String> {

        return listOf()
    }
}