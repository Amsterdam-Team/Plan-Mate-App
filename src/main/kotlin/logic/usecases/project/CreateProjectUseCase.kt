package logic.usecases.project

import logic.entities.Project
import logic.repository.ProjectRepository

class CreateProjectUseCase(private val repository: ProjectRepository) {
    fun createProject(project: Project): Boolean{
        //Todo : logic implementation
    return true}
}