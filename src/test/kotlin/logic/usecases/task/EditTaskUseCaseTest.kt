package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import data.repository.task.TaskRepositoryImpl
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import logic.entities.Task
import logic.exception.PlanMateException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.ValidationException.InvalidProjectNameException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.usecases.ValidateInputUseCase
import logic.usecases.testFactory.CreateTaskTestFactory
import java.util.*

class EditTaskUseCaseTest {
    lateinit var repository: TaskRepositoryImpl
    lateinit var usecase: EditTaskUseCase
    lateinit var validation: ValidateInputUseCase
    lateinit var taskId: String
    lateinit var task: Task

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        validation = ValidateInputUseCase()
        usecase = EditTaskUseCase(repository, validation)
        taskId = UUID.randomUUID().toString()
        task = Task(
            id = UUID.randomUUID(),
            name = "add test",
            state = "todo",
            projectId = UUID.randomUUID()
        )

    }

    @Test
    fun `should return true when editing task function complete successfully`() {
        every { repository.updateTaskNameByID(task.id, "new name") } returns true
        every { repository.updateStateNameByID(task.id, "new state") } returns true
        val result = usecase.editTask(task.id.toString(), "new name", "new state")
        assertThat(result).isTrue()
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() {

    }

    @Test
    fun `should throw not valid uuid when user enter not valid id `() {
        every { repository.updateTaskNameByID(task.id, "new name") } returns true
        every { repository.updateStateNameByID(task.id, "new state") } returns true
        assertThrows<InvalidTaskIDException> {
            usecase.editTask("not valid id", "new name", "new state")

        }

    }

    @Test
    fun `should throw invalid task name when trying to add name contain not alphabet characters`() {
        every { repository.updateTaskNameByID(task.id, "new#$") } returns true
        every { repository.updateStateNameByID(task.id, "new state") } returns true
        assertThrows<InvalidTaskNameException> {
            usecase.editTask(task.id.toString(), "new#$", "new state")

        }
    }




}