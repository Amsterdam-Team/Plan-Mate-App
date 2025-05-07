package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
import logic.usecases.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class GetTaskByIdUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskByIdUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetTaskByIdUseCase(repository)
    }

    @Test
    fun `getTaskById should  get task by taskId from repository when called`() {
        // given
        val taskId = UUID.fromString("123e4567-e89b-12d3-a456-000000000000")
        val taskOne = CreateTaskTestFactory.validTask.copy(id = taskId)
        every { repository.getTaskById(taskId) } returns taskOne

        //When
        useCase(taskId)

        // Then
        verify(exactly = 1) { repository.getTaskById(taskId) }
    }

    @Test
    fun `should get task by task id when task is exists`() {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(id = projectId)

        every { repository.getTaskById(projectId) } returns taskOne

        // when
        val result = useCase(projectId)

        //Then
        assertThat(result).isEqualTo(taskOne)
    }


    @Test
    fun `should throw NoTaskFoundException when there are no task for provided task id`() {
        // given
        val taskId = UUID.randomUUID()
        every { repository.getTaskById(taskId) } throws TaskNotFoundException

        // when && then
        assertThrows<TaskNotFoundException> {
            useCase(taskId)
        }
    }


    @Test
    fun `should return task when task with given id exists`() {
        // given
        val taskId = UUID.randomUUID()

        val task = CreateTaskTestFactory.validTask.copy(id = taskId)

        every { repository.getTaskById(taskId) } returns task

        // when
        val result = useCase(taskId)

        // then
        assertThat(result).isEqualTo(task)
    }


}