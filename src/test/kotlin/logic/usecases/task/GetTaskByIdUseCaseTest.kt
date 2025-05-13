package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import helper.TaskFactory.validTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.ITaskRepository
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GetTaskByIdUseCaseTest {
    private lateinit var repository: ITaskRepository
    private lateinit var useCase: GetTaskByIdUseCase
    private lateinit var validateInputUseCase: ValidateInputUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        validateInputUseCase = mockk(relaxed = true)
        useCase = GetTaskByIdUseCase(repository, validateInputUseCase)
        coEvery { validateInputUseCase.isValidUUID(any()) } returns true
    }

    @Test
    fun `getTaskById should  get task by taskId from repository when called`() = runTest {
        // given
        val taskId = UUID.fromString("123e4567-e89b-12d3-a456-000000000000")
        val taskOne = validTask.copy(id = taskId)
        coEvery { repository.getTaskById(taskId) } returns taskOne

        //When
        useCase(taskId.toString())

        // Then
        coVerify(exactly = 1) { repository.getTaskById(taskId) }
    }

    @Test
    fun `should get task by task id when task is exists`() = runTest {
        // given
        val taskId = UUID.randomUUID()
        val taskOne = validTask.copy(id = taskId)

        coEvery { repository.getTaskById(taskId) } returns taskOne

        // when
        val result = useCase(taskId.toString())

        //Then
        assertThat(result).isEqualTo(taskOne)
    }


    @Test
    fun `should throw NoTaskFoundException when there are no task for provided task id`() =
        runTest {
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

        val task = validTask.copy(id = taskId)

        coEvery { repository.getTaskById(taskId) } returns task

        // when
        val result = useCase(taskId.toString())

        // then
        assertThat(result).isEqualTo(task)
    }


}