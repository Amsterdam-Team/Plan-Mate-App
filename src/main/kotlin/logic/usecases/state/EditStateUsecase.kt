package logic.usecases.state

import logic.entities.Project
import logic.repository.ProjectRepository
import java.util.UUID

class EditStateUsecase(
    val projectRepository: ProjectRepository
) {
    fun editState(projectID:UUID,oldState:String,newState:String){

    }
}