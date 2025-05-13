package logic.usecases.task

import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.NotFoundException.StateNotFoundException
import logic.repository.IProjectRepository
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.ValidateInputUseCase
import java.util.UUID

class CreateTaskUseCase(
    private val taskRepository: ITaskRepository,
    private val projectRepository: IProjectRepository,
    private val validateInputUseCase: ValidateInputUseCase,
    private val loggerUseCase: LoggerUseCase
) {

    suspend fun createTask(name: String, projectId: String, state: String): Boolean {
        requireOrThrow(
            condition = validateInputUseCase.isValidName(name),
            exception = InvalidTaskNameException
        )
        requireOrThrow(
            condition = validateInputUseCase.isValidUUID(uuid = projectId),
            exception = InvalidProjectIDException
        )
        requireOrThrow(
            condition = validateInputUseCase.isValidName(state),
            exception = InvalidStateNameException
        )

        val targetProject = projectRepository.getProjectById(UUID.fromString(projectId))
        requireOrThrow(
            condition = isStateInProject(state = state, projectStates = targetProject.states),
            exception = StateNotFoundException
        )
        val taskUUID = UUID.randomUUID()
        return taskRepository.createTask(
            Task(
                id = taskUUID,
                name = name,
                projectId = targetProject.id,
                state = state
            )
        ).also { isCreated ->
            if(isCreated) loggerUseCase.createLog("$CREATED_KEYWORD $name $TASK_KEYWORD",taskUUID)
        }
    }

    private fun requireOrThrow(condition: Boolean, exception: PlanMateException) {
        if (!condition) throw exception
    }

    private fun isStateInProject(state: String, projectStates: List<String>): Boolean {
        return state in projectStates
    }

    companion object{
        const val  CREATED_KEYWORD = "Created"
        const val  TASK_KEYWORD = "task"
    }
}