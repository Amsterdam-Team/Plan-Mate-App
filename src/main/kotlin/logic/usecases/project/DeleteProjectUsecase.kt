package logic.usecases.project

import logic.repository.ProjectRepository

class DeleteProjectUsecase(val projectRepository: ProjectRepository){

    fun deleteProject(projectId: String) {
        throw Exception("unimplemented yet")
    }

}