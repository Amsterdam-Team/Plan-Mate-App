package logic.usecases

import helpers.DeleteTaskTestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.entities.LogItem
import logic.entities.Task
import logic.exception.PlanMateException
import logic.repository.LogRepository
import logic.repository.TaskRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class DeleteTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    }

    @Test
    fun `should call deleteTask when task is found`() {
        // Given
        val taskId = UUID.randomUUID()
        val task = DeleteTaskTestFactory.TASK_1
        every { taskRepository.getTaskById(taskId) } returns task

        // When
        deleteTaskUseCase.execute(taskId)

        // Then
        verify(exactly = 1) { taskRepository.deleteTask(taskId) }
    }



    @Test
    fun `should return TaskNotFoundException when task is not found`() {
        // Given
        val taskId = UUID.randomUUID()
        every { taskRepository.getTaskById(taskId) } throws PlanMateException.NotFoundException.TaskNotFoundException

        // When & Then
        assertThrows<PlanMateException.NotFoundException.TaskNotFoundException> {
            deleteTaskUseCase.execute(taskId)
        }
    }

}
