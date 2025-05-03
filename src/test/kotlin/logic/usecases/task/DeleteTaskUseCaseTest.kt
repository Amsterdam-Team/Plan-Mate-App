package logic.usecases.task

import helpers.DeleteTaskTestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.repository.TaskRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = DeleteTaskUseCase(repository)
    }
//region success
    @Test
    fun `should call deleteTask when task is found`() {
        // Given
        val task = DeleteTaskTestFactory.TASK_1
        every { repository.getTaskById(task.id) } returns task

        // When
        useCase.execute(task.id.toString())

        // Then
    verify(exactly = 1) { repository.deleteTask(task.id) }
    }
//endregion
//region task validations
    @Test
    fun `should throw InvalidTaskIDException when input is null`() {
        // Given
        val task = DeleteTaskTestFactory.TASK_1
        every { repository.getTaskById(task.id) } returns task
        // When & Then
    assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
        useCase.execute(null)
    }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not a uuid`() {
        // Given
        val task = DeleteTaskTestFactory.TASK_1
        every { repository.getTaskById(task.id) } returns task
        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            useCase.execute("not-uuid")
        }
    }

//endregion


}