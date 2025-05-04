package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.repository.TaskRepository
import java.util.UUID

class EditTaskUseCase(val taskRepository: TaskRepository) {


    fun editTask(taskId: String, newName:String, newState:String): Boolean{
        validateStringInput(newName)
        validateStringInput(newState)
        validateId(taskId)
       return  taskRepository.updateTaskNameByID(UUID.fromString(taskId), newName)
                && taskRepository.updateStateNameByID(UUID.fromString(taskId), newState)
    }



    fun validateStringInput(name:String){
        if (name.isEmpty() || name.isBlank()){
            throw EmptyDataException
        }
        if (name.contains(regex = Regex(pattern = "[^a-zA-Z\\s]"))){
            throw InvalidTaskNameException
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