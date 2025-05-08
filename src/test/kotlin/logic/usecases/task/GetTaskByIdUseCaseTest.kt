package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
import logic.usecases.utils.ValidateInputUseCase
import logic.usecases.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class GetTaskByIdUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskByIdUseCase
    private lateinit var validateInputUseCase: ValidateInputUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        validateInputUseCase =mockk(relaxed = true)
        useCase = GetTaskByIdUseCase(repository,validateInputUseCase)
    }

    @Test
    fun `getTaskById should  get task by taskId from repository when called`() = runTest {
        // given
        val taskId = UUID.fromString("123e4567-e89b-12d3-a456-000000000000")
        val taskOne = CreateTaskTestFactory.validTask.copy(id = taskId)
        coEvery { repository.getTaskById(taskId) } returns taskOne

        //When
        useCase(taskId.toString())

        // Then
        coVerify (exactly = 1) { repository.getTaskById(taskId) }
    }

    @Test
    fun `should get task by task id when task is exists`() = runTest {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(id = projectId)

        coEvery { repository.getTaskById(projectId) } returns taskOne

        // when
        val result = useCase(projectId.toString())

        //Then
        assertThat(result).isEqualTo(taskOne)
    }


    @Test
    fun `should throw NoTaskFoundException when there are no task for provided task id`()= runTest  {
        // given
        val taskId = UUID.randomUUID()
        coEvery { repository.getTaskById(taskId) } throws TaskNotFoundException

        // when && then
        assertThrows<TaskNotFoundException> {
            useCase(taskId.toString())
        }
    }


    @Test
    fun `should return task when task with given id exists`() = runTest {
        // given
        val taskId = UUID.randomUUID()

        val task = CreateTaskTestFactory.validTask.copy(id = taskId)

        coEvery { repository.getTaskById(taskId) } returns task

        // when
        val result = useCase(taskId.toString())

        // then
        assertThat(result).isEqualTo(task)
    }


}