package logic.usecases.task

import com.google.common.truth.Truth.assertThat
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
    fun `should call deleteTask when parameters is correct`() {
        // Given
        val task = DeleteTaskTestFactory.TASK_1
        // When
        useCase.execute(task.id.toString())

        // Then
        verify(exactly = 1) { repository.deleteTask(task.id) }
    }
    @Test
    fun `should returns true when task is deleted successfully`() {
        // Given
        val task = DeleteTaskTestFactory.TASK_1
        every { repository.deleteTask(task.id) } returns true
        // When
        val result  = useCase.execute(task.id.toString())

        // Then
        assertThat(result).isTrue()
    }
//endregion
//region task validations

    @Test
    fun `should returns false when task is not deleted`() {
        // Given
        val task = DeleteTaskTestFactory.TASK_1
        every { repository.deleteTask(task.id) } returns false
        // When
        val result  = useCase.execute(task.id.toString())

        // Then
        assertThat(result).isFalse()
    }
    @Test
    fun `should throw InvalidTaskIDException when input is null`() {
        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            useCase.execute(null)
        }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not a uuid`(){
        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            useCase.execute("not-uuid")
        }
    }

//endregion


}