package logic.usecases

import helpers.DeleteTaskTestFactory.TASK_1
import helpers.DeleteTaskTestFactory.TASK_3
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.TaskRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    }
//region success
    @Test
    fun `should call deleteTask when task is found`() {
        // Given
        val task = TASK_1
        every { taskRepository.getTaskById(task.id) } returns task

        // When
        deleteTaskUseCase.execute(task.id.toString())

        // Then
        verify(exactly = 1) { taskRepository.deleteTask(task.id) }
    }
//endregion
//region task validations
    @Test
    fun `should throw InvalidTaskIDException when input is null`() {
        // Given
        val task = TASK_1
        every { taskRepository.getTaskById(task.id) } returns task
        // When & Then
        assertThrows<InvalidTaskIDException> {
            deleteTaskUseCase.execute(null)
        }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not a uuid`() {
        // Given
        val task = TASK_1
        every { taskRepository.getTaskById(task.id) } returns task
        // When & Then
        assertThrows<InvalidTaskIDException> {
            deleteTaskUseCase.execute("not-uuid")
        }
    }



    @Test
    fun `should throw TaskNotFoundException when the task is not found`() {
        // Given
        val task = TASK_3
        every { taskRepository.getTaskById(task.id) } throws TaskNotFoundException
        // When & Then
        assertThrows<TaskNotFoundException> {
            deleteTaskUseCase.execute(task.id.toString())
        }
    }


//endregion


}
