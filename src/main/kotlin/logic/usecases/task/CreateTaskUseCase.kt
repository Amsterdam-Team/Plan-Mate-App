package logic.usecases.task

import logic.entities.LogItem
import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.NotFoundException.StateNotFoundException
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import java.util.UUID

class CreateTaskUseCase(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository
) {

    fun createTask(name: String, projectId: String, state: String) {
        requireOrThrow(condition = isValidName(name), exception = InvalidTaskNameException)
        requireOrThrow(condition = isValidUUID(uuid = projectId), exception = InvalidProjectIDException)
        requireOrThrow(condition = isValidName(state), exception = InvalidStateNameException)

        val targetProject = projectRepository.getProject(UUID.fromString(projectId))
        requireOrThrow(
            condition = isStateInProject(state = state, projectStates = targetProject.states),
            exception = StateNotFoundException
        )
        val taskId =UUID.randomUUID()
        taskRepository.createTask(
            Task(
                id = taskId,
                name = name,
                projectId = targetProject.id,
                state = state
            )
        )
        logRepository.addLog(
            "create a new task ${name} in ${targetProject.name} project",
            taskId
        )

    }

    private fun requireOrThrow(condition: Boolean, exception: PlanMateException) {
        if (!condition) throw exception
    }

    private fun isValidName(name: String): Boolean {
        return name.isNotBlank() &&
                name.length in 3..100 &&
                name.matches(Regex("^[\\w\\s-]+$"))
    }


    private fun isValidUUID(uuid: String) = runCatching { UUID.fromString(uuid) }.isSuccess

    private fun isStateInProject(state: String, projectStates: List<String>): Boolean {
        return state in projectStates
    }

}

