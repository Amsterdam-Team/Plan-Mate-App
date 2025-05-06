package logic.usecases.task

import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.repository.TaskRepository
import logic.usecases.ValidateInputUseCase
import java.util.UUID

class EditTaskUseCase(val taskRepository: TaskRepository, val validateInputUseCase: ValidateInputUseCase) {


    fun editTask(taskId: String, newName: String, newState: String): Boolean {
        validateTaskInputs(taskId, newName, newState)

        val uuid = parseId(taskId)

        val existingTask = taskRepository.getTaskById(uuid)

        var updated = false

        if (existingTask.name != newName) {
            updated = taskRepository.updateTaskNameByID(uuid, newName) || updated
        }

        if (existingTask.state != newState) {
            updated = taskRepository.updateStateNameByID(uuid, newState) || updated
        }

        return updated
    }



    private fun validateTaskInputs(taskId:String ,name:String, state:String): Boolean{
        if (! validateInputUseCase.isValidName(name)){
            throw InvalidTaskNameException
        }
        if(! validateInputUseCase.isValidName(state)){
            throw InvalidStateNameException
        }
        if (! validateInputUseCase.isValidUUID(taskId)){
            throw InvalidTaskIDException
        }
        return true

    }

    private fun parseId(id: String): UUID {
        if (id.isBlank()) throw EmptyDataException

        return try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw InvalidTaskIDException
        }
    }
}