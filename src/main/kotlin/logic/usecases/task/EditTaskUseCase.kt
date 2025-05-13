package logic.usecases.task

import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class EditTaskUseCase(
    private val taskRepository: ITaskRepository,
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

            loggerUseCase.createLog("$UPDATE_TASK_KEYWORD ${existingTask.name} $NAME_TO_KEYWORD $newName", taskUUID)

            hasChanges = true
        }

        if (existingTask.state != newState) {
            val stateUpdated = taskRepository.updateStateNameByID(taskUUID, newState)
            if (!stateUpdated) throw TaskNotFoundException

            loggerUseCase.createLog(
                "$UPDATE_TASK_KEYWORD ${existingTask.name} $STATE_FROM_KEYWORD ${existingTask.state} $TO_KEYWORD $newState",
                taskUUID
            )

            hasChanges = true
        }

        return hasChanges
    }


   private fun validateTaskInputs(taskId: String, name: String, state: String): Boolean {
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

    companion object{
        const val  UPDATE_TASK_KEYWORD = "Updated task"
        const val  NAME_TO_KEYWORD = "name to"
        const val  STATE_FROM_KEYWORD = "state from"
        const val TO_KEYWORD = "to"
    }
}