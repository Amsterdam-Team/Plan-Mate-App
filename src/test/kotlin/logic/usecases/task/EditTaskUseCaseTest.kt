package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import data.repository.task.TaskRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.Task
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.Test

class EditTaskUseCaseTest {
    lateinit var repository: TaskRepositoryImpl
    lateinit var useCase: EditTaskUseCase
    lateinit var validation: ValidateInputUseCase
    lateinit var loggerUseCase: LoggerUseCase
    lateinit var taskId: String
    lateinit var task: Task

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        validation = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        useCase = EditTaskUseCase(repository, validation, loggerUseCase)
        taskId = UUID.randomUUID().toString()
        task = Task(
            id = UUID.randomUUID(),
            name = "test task",
            state = "TODO",
            projectId = UUID.randomUUID()
        )

    }

    @Test
    fun `should return true when editing task function complete successfully`() = runTest {
        coEvery { validation.isValidUUID(task.id.toString()) } returns true
        coEvery { validation.isValidName("new name") } returns true
        coEvery { validation.isValidName("new state") } returns true
        coEvery { repository.getTaskById(task.id) } returns task
        coEvery { repository.updateTaskNameByID(task.id, "new name") } returns true
        coEvery { repository.updateStateNameByID(task.id, "new state") } returns true

        val result = useCase.editTask(task.id.toString(), "new name", "new state")

        assertThat(result).isTrue()
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() = runTest {
        // Given
        coEvery { validation.isValidUUID(task.id.toString()) } returns true
        coEvery { validation.isValidName("new name") } returns true
        coEvery { validation.isValidName("new state") } returns true

        coEvery { repository.getTaskById(task.id) } returns task
        coEvery { repository.updateTaskNameByID(task.id, "new name") } returns false
        coEvery { repository.updateStateNameByID(task.id, "new state") } returns true

        // When & Then
        assertThrows<TaskNotFoundException> {
            useCase.editTask(task.id.toString(), "new name", "new state")
        }
    }

    @Test
    fun `should throw not valid uuid when user enter not valid id `() = runTest {
        coEvery { validation.isValidUUID("not valid id") } returns false
        coEvery { repository.updateTaskNameByID(task.id, "new name") } returns true
        coEvery { repository.updateStateNameByID(task.id, "new state") } returns true
        assertThrows<InvalidTaskIDException> {
            useCase.editTask("not valid id", "new name", "new state")

        }

    }

    @Test
    fun `should throw invalid task name when trying to add name contain not alphabet characters`() =
        runTest {
            coEvery { validation.isValidUUID(task.id.toString()) } returns true
            coEvery { validation.isValidName("new#$") } returns false

            assertThrows<InvalidTaskNameException> {
                useCase.editTask(task.id.toString(), "new#$", "new state")
            }
        }


}