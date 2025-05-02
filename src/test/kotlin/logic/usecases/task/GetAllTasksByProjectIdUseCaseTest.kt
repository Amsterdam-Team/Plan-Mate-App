package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException
import logic.repository.TaskRepository
import logic.usecases.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class GetAllTasksByProjectIdUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetAllTasksByProjectIdUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetAllTasksByProjectIdUseCase(repository)
    }


    @Test
    fun `getAllTasksByProjectId should  get all tasks by projectId  from repository when called`() {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskTwo = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        every { repository.getAllTasksByProjectId(projectId) } returns listOf(taskOne, taskTwo)
        // when
        useCase(projectId)
        // Then
        verify(exactly = 1) { repository.getAllTasksByProjectId(projectId) }
    }

    @Test
    fun `should get tasks by project id when project is exists`() {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskTwo = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        every { repository.getAllTasksByProjectId(projectId) } returns listOf(taskOne, taskTwo)
        // when
        val result = useCase(projectId)
        // then
        assertThat(result).containsExactly(taskOne, taskTwo)
    }


    @Test
    fun `should return tasks filtered by project ID`() {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskTwo = CreateTaskTestFactory.validTask.copy(projectId = projectId)


        every { repository.getAllTasksByProjectId(projectId) } returns listOf(taskOne, taskTwo)

        // when
        val result = useCase(projectId)

        // then
        assertThat(result).containsExactly(taskOne, taskTwo)
    }

    @Test
    fun `should throw NoTasksFoundException when there are no tasks for provided project id`() {
        // given
        val projectId = UUID.randomUUID()
        val anotherProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

        every { repository.getAllTasksByProjectId(projectId) } throws NotFoundException.TaskNotFoundException

        // when && then
        assertThrows<PlanMateException.NotFoundException.TaskNotFoundException> {
            useCase(projectId)
        }
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        val anotherProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

        // Given
        every { repository.getAllTasksByProjectId(anotherProjectID) } throws NotFoundException.ProjectNotFoundException

        //When & Then
        assertThrows<NotFoundException.ProjectNotFoundException> { useCase(anotherProjectID) }
    }
}