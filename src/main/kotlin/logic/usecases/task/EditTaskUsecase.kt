package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.repository.LogRepository
import logic.repository.TaskRepository
import java.util.UUID

class EditTaskUseCase(val taskRepository: TaskRepository,  val logRepository: LogRepository) {

    fun editTaskName(taskId: String, newName: String):Boolean {
        validateStringInput(newName)
        validateId(taskId)
        val task = taskRepository.getTaskById(UUID.fromString(taskId))

        taskRepository.updateTask(task.copy(name = newName))
        logRepository.addLog(
            "edit task name be ${newName}",
            UUID.fromString(taskId)

        )
        return true
    }

    fun editTaskNameAndState(taskId: String, newName: String, newState :String): Boolean{
        validateStringInput(newName)
        validateStringInput(newState)
        validateId(taskId)
        val task = taskRepository.getTaskById(UUID.fromString(taskId))
        taskRepository.updateTask(task.copy(name = newName, state = newState))
        logRepository.addLog(
            "edit task name and state to be ${newName}, ${newState}",
            UUID.fromString(taskId)

        )
        return true
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