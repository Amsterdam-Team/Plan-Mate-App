package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException
import logic.repository.TaskRepository
import logic.usecases.task.testFactory.CreateTaskTestFactory.createTaskWithProjectID
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
        //When
        useCase("1")
        // Then
        verify(exactly = 1) { repository.getTaskById("1") }
    }

    @Test
    fun `should get task by task id when task is exists`() {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = createTaskWithProjectID(projectId = projectId)
        every { repository.getTaskById("$projectId") } returns taskOne

        // when
        val result = useCase("$projectId")

        //Then
        assertThat(result).isEqualTo(taskOne)
    }


    @Test
    fun `should throw NoTaskFoundException when there are no task for provided task id`() {
        // given
        val projectId = UUID.randomUUID()
        val anotherProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.getTaskById("$projectId") } throws NotFoundException.TaskNotFoundException

        // when && then
        assertThrows<PlanMateException.NotFoundException.TaskNotFoundException> {
            useCase("$anotherProjectID")
        }
    }

}