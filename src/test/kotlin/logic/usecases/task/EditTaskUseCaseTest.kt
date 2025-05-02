package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import data.repository.task.TaskRepositoryImpl
import io.mockk.mockk
import logic.exception.PlanMateException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.ValidationException.InvalidProjectNameException
import java.util.*

class EditTaskUseCaseTest {
    lateinit var repository: TaskRepositoryImpl
    lateinit var usecase: EditTaskUseCase
    lateinit var taskId: String

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        usecase = EditTaskUseCase(repository)
        taskId = UUID.randomUUID().toString()
    }

    @Test
    fun `should return true when editing task function complete successfully`() {

        // when
        val result = usecase.editTask(taskId, "new name")

        assertThat(result).isTrue()
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() {

        assertThrows<TaskNotFoundException> {
            usecase.editTask(taskId, "new name")
        }
    }

    @Test
    fun `should throw not valid uuid when trying to parse not valid uuid`() {
        //TODO: refactor name to better name


        assertThrows<IllegalFormatException> {
            usecase.editTask(taskId, "new name")
        }
    }

    @Test
    fun `should throw invalid task name when trying to add name contain not alphabet characters`() {
        //TODO: refactor name to better name


        assertThrows<InvalidProjectNameException> {
            usecase.editTask(taskId, "new name@#$%%#")
        }
    }


}