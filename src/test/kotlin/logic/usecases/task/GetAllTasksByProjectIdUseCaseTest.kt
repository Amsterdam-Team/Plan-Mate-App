package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
import logic.usecases.ValidateInputUseCase
import logic.usecases.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class GetAllTasksByProjectIdUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetAllTasksByProjectIdUseCase
    private lateinit var validateInputUseCase: ValidateInputUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        validateInputUseCase = mockk(relaxed = true)
        useCase = GetAllTasksByProjectIdUseCase(repository,validateInputUseCase)
    }


    @Test
    fun `getAllTasksByProjectId should  get all tasks by projectId  from repository when called`() = runTest {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskTwo = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        coEvery { repository.getAllTasksByProjectId(projectId) } returns listOf(taskOne, taskTwo)
        // when
        useCase(projectId.toString())
        // Then
        coVerify (exactly = 1) { repository.getAllTasksByProjectId(projectId) }
    }

    @Test
    fun `should get tasks by project id when project is exists`() = runTest {
        // given
        val anotherProjectID = UUID.fromString("123e4567-e89b-12d3-a456-000000000000")

        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskTwo = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskAnother = CreateTaskTestFactory.validTask.copy(projectId = anotherProjectID)
        coEvery { repository.getAllTasksByProjectId(projectId) } returns listOf(taskOne, taskTwo, taskAnother)
        // when
        val result = useCase(projectId.toString())
        // then
        assertThat(result).containsExactly(taskTwo, taskTwo)
    }


    @Test
    fun `should return tasks filtered by project ID`() = runTest {
        // given
        val projectId = UUID.randomUUID()
        val taskOne = CreateTaskTestFactory.validTask.copy(projectId = projectId)
        val taskTwo = CreateTaskTestFactory.validTask.copy(projectId = projectId)


        coEvery { repository.getAllTasksByProjectId(projectId) } returns listOf(taskOne, taskTwo)

        // when
        val result = useCase(projectId.toString())

        // then
        assertThat(result).containsExactly(taskOne, taskTwo)
    }

    @Test
    fun `should throw NoTasksFoundException when there are no tasks for provided project id`() = runTest {
        // given
        val projectId = UUID.randomUUID()

        coEvery { repository.getAllTasksByProjectId(projectId) } throws TaskNotFoundException

        // when && then
        assertThrows<TaskNotFoundException> {
            useCase(projectId.toString())
        }
    }

    @Test
    fun `should throw TaskNotFoundException when no tasks exist for given project id`() = runTest {
        // given
        val projectId = UUID.randomUUID()
        coEvery { repository.getAllTasksByProjectId(projectId) } returns emptyList()

        // when & then
        assertThrows<TaskNotFoundException> {
            useCase(projectId.toString())
        }
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() = runTest {
        val anotherProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

        // Given
        coEvery { repository.getAllTasksByProjectId(anotherProjectID) } throws ProjectNotFoundException

        //When & Then
        assertThrows<ProjectNotFoundException> { useCase(anotherProjectID.toString()) }
    }
}