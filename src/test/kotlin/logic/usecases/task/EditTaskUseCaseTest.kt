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
import logic.usecases.task.testFactory.CreateTaskTestFactory
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
        val result = usecase.editTaskName(taskId, "new name")

        assertThat(result).isTrue()
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() {
        val task = CreateTaskTestFactory.validTask
        every { repository.getTaskById(task.id)} throws TaskNotFoundException


        assertThrows<TaskNotFoundException> {
            usecase.editTaskName(task.id.toString(), "new name")
        }
    }

    @Test
    fun `should throw not valid uuid when user enter not valid id `() {

        val task = CreateTaskTestFactory.validTask
        every { repository.getTaskById(any())} returns task


        assertThrows<InvalidTaskIDException> {
            usecase.editTaskName("invalidId", "new name")
        }
    }

    @Test
    fun `should throw invalid task name when trying to add name contain not alphabet characters`() {
        val task = CreateTaskTestFactory.validTask
        every { repository.getTaskById(any())} returns task


        assertThrows<InvalidTaskNameException> {
            usecase.editTaskName(taskId, "new name $%")
        }
    }
    @Test
    fun `should throw invalid task name when trying to add name contain numbers`() {
//        val task = CreateTaskTestFactory.validTask
//        every { repository.getTaskById(any())} returns task


        assertThrows<InvalidTaskNameException> {
            usecase.editTaskName(taskId, "new name3")
        }
    }


}