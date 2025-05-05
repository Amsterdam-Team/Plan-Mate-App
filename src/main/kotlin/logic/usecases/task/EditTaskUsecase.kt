package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.repository.TaskRepository
import logic.usecases.ValidateInputUseCase
import java.util.UUID

class EditTaskUseCase(val taskRepository: TaskRepository) {


    fun editTask(taskId: String, newName: String, newState: String): Boolean {
        validateStringInput(newName)
        validateStringInput(newState)

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

    private fun validateStringInput(input: String) {
        if (input.isBlank()) throw EmptyDataException

        val regex = Regex("^[\\w\\s-]+\$") // allows letters, numbers, underscores, hyphens, and spaces
        if (!regex.matches(input)) {
            throw InvalidTaskNameException
        }
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