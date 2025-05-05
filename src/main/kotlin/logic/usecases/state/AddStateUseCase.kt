package logic.usecases.state

import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ProjectRepository
import utils.ResultStatus
import java.util.UUID

class AddStateUseCase(
    private val repository: ProjectRepository
) {

    fun execute(projectId: String, state: String): Boolean{
        validateState(state)
        validateId(projectId)
        return repository.addStateById(UUID.fromString(projectId), state)

    }
    private fun validateState(state :String){
        if(state.isBlank()|| state.isEmpty() || state.contains(regex = Regex(pattern ="[^a-zA-Z\\s]" )) ){
            // match any non alphabet characters
            throw InvalidStateNameException
        }
    }
    fun validateId(id:String){
        if (id.isEmpty() || id.isBlank()){
            throw EmptyDataException
        }

        try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException){
            throw InvalidTaskIDException
        }
    }
}