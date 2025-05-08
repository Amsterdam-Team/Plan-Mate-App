package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import data.repository.task.TaskRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.Task
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.ValidateInputUseCase
import java.util.*

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
        validation = ValidateInputUseCase()
        useCase = EditTaskUseCase(repository, validation,loggerUseCase)
        taskId = UUID.randomUUID().toString()
        task = Task(
            id = UUID.randomUUID(),
            name = "add test",
            state = "todo",
            projectId = UUID.randomUUID()
        )

    }

    @Test
    fun `should return true when editing task function complete successfully`() = runTest{
        coEvery { repository.updateTaskNameByID(task.id, "new name") } returns true
        coEvery { repository.updateStateNameByID(task.id, "new state") } returns true
        val result = useCase.editTask(task.id.toString(), "new name", "new state")
        assertThat(result).isTrue()
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() = runTest{

    }

    @Test
    fun `should throw not valid uuid when user enter not valid id `() =runTest{
        coEvery { repository.updateTaskNameByID(task.id, "new name") } returns true
        coEvery { repository.updateStateNameByID(task.id, "new state") } returns true
        assertThrows<InvalidTaskIDException> {
            useCase.editTask("not valid id", "new name", "new state")

        }

    }

    @Test
    fun `should throw invalid task name when trying to add name contain not alphabet characters`() =runTest{
        coEvery { repository.updateTaskNameByID(task.id, "new#$") } returns true
        coEvery { repository.updateStateNameByID(task.id, "new state") } returns true
        assertThrows<InvalidTaskNameException> {
            useCase.editTask(task.id.toString(), "new#$", "new state")

        }
    }




}