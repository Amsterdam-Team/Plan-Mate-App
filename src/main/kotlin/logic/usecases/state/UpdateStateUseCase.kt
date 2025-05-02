package logic.usecases.state

import logic.repository.ProjectRepository
import java.util.UUID

class UpdateStateUseCase(
    val projectRepository: ProjectRepository
) {
    fun updateState(projectID:UUID,oldState:String,newState:String){

    }
}