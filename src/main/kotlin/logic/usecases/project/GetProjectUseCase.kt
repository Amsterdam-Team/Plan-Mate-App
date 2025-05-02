package logic.usecases.project

import logic.entities.Project
import logic.repository.ProjectRepository
import java.util.UUID

class GetProjectUseCase(
    private val projectRepository: ProjectRepository
) {

    fun getProject(projectID:UUID):Project{

        return Project(UUID.fromString("2b19ee75-2b4c-430f-bad8-dfa6b14709d9"),"", listOf(), listOf())
    }

}