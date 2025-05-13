package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import helper.TaskFactory.TASK_1
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteTaskUseCaseTest {
    private lateinit var repository: ITaskRepository
    private lateinit var useCase: DeleteTaskUseCase
    private lateinit var loggerUseCase: LoggerUseCase

    @BeforeEach
    fun setup() =runTest {
        repository = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        useCase = DeleteTaskUseCase(repository,loggerUseCase)
    }
//region success
    @Test
    fun `should call deleteTask when parameters is correct`() =runTest {
        // Given
        val task = TASK_1
        // When
        useCase.execute(task.id.toString())

        // Then
        coVerify (exactly = 1)  { repository.deleteTask(task.id) }
    }
    @Test
    fun `should returns true when task is deleted successfully`() =runTest {
        // Given
        val task = TASK_1
        coEvery { repository.deleteTask(task.id) } returns true
        // When
        val result  = useCase.execute(task.id.toString())

        // Then
        assertThat(result).isTrue()
    }
//endregion
//region task validations

    @Test
    fun `should returns false when task is not deleted`() =runTest {
        // Given
        val task = TASK_1
        coEvery { repository.deleteTask(task.id) } returns false
        // When
        val result  = useCase.execute(task.id.toString())

        // Then
        assertThat(result).isFalse()
    }
    @Test
    fun `should throw InvalidTaskIDException when input is null`() =runTest {
        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            useCase.execute(null)
        }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not a uuid`() =  runTest{
        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            useCase.execute("not-uuid")
        }
    }

//endregion


}