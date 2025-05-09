package logic.usecases.task

import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.repository.TaskRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class EditTaskUseCase(
    private val taskRepository: TaskRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val loggerUseCase: LoggerUseCase
) {


    suspend fun editTask(taskId: String, newName: String, newState: String): Boolean {
        validateTaskInputs(taskId, newName, newState)

        val taskUUID = parseId(taskId)
        val existingTask = taskRepository.getTaskById(taskUUID)

        var hasChanges = false

        if (existingTask.name != newName) {
            val nameUpdated = taskRepository.updateTaskNameByID(taskUUID, newName)
            if (!nameUpdated) throw TaskNotFoundException

            loggerUseCase.createLog("Updated task ${existingTask.name} name to $newName", taskUUID)

            hasChanges = true
        }

        if (existingTask.state != newState) {
            val stateUpdated = taskRepository.updateStateNameByID(taskUUID, newState)
            if (!stateUpdated) throw TaskNotFoundException

            loggerUseCase.createLog(
                "Updated task ${existingTask.name} state from ${existingTask.state} to $newState",
                taskUUID
            )

            hasChanges = true
        }

        return hasChanges
    }


    fun validateTaskInputs(taskId: String, name: String, state: String): Boolean {
        if (!validateInputUseCase.isValidUUID(taskId)) {
            throw InvalidTaskIDException
        }
        if (!validateInputUseCase.isValidName(name)) {
            throw InvalidTaskNameException
        }
        if (!validateInputUseCase.isValidName(state)) {
            throw InvalidStateNameException
        }
        return true
    }


    private fun parseId(id: String): UUID {
        if (id.isBlank()) throw EmptyDataException

        return try {
            UUID.fromString(id)
        } catch (_: IllegalArgumentException) {
            throw InvalidTaskIDException
        }
    }
}