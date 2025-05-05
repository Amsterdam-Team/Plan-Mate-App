package logic.usecases.state

import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ProjectRepository
import logic.usecases.ValidateInputUseCase
import utils.ResultStatus
import java.util.UUID

class AddStateUseCase(
    private val repository: ProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase
) {

    fun execute(projectId: String, state: String): Boolean{
        if(!validateInputUseCase.isValidName(state)){
            println("state name must not be empty, it can contain underscore, hyphen, digits but not special characters")
        }
        validateId(projectId)
        return repository.addStateById(UUID.fromString(projectId), state)

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