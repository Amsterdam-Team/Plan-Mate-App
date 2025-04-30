package logic.usecases.task


import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.*
import logic.repository.TaskRepository
import logic.usecases.task.testFactory.CreateTaskTestFactory.taskWithWrongProjectID
import logic.usecases.task.testFactory.CreateTaskTestFactory.validTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class CreateTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: CreateTaskUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = CreateTaskUseCase(repository)
    }

    @Test
    fun `should create task when all input are valid`() {
        //Given
        every { repository.createTask(validTask) } returns Unit

        //When & Then
        assertDoesNotThrow { useCase.createTask(validTask) }
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() {
        // Given
        every { repository.createTask(taskWithWrongProjectID) } throws  ProjectNotFoundException

        //When & Then
        assertThrows<ProjectNotFoundException> { useCase.createTask(taskWithWrongProjectID) }
    }
}