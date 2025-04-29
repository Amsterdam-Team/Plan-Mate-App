package logic.usecases.task

import data.repository.project.ProjectRepositoryImpl
import data.repository.task.TaskRepositoryImpl
import io.mockk.mockk
import logic.exception.PlanMateException
import logic.usecases.project.DeleteProjectUsecase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import logic.exception.PlanMateException.NotFoundException.*
import java.util.*

class EditTaskUsecaseTest {
    lateinit var projectRepository: TaskRepositoryImpl
    lateinit var editTaskUsecase: EditTaskUsecase

    @BeforeEach
    fun setUp() {
        projectRepository = mockk(relaxed = true)
        editTaskUsecase = EditTaskUsecase(projectRepository)
    }

    @Test
    fun `should edit task name successfully when the task exists`() {

        assertDoesNotThrow {
            editTaskUsecase.editTask("43r34ferc", "new name")
        }
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() {

        assertThrows<TaskNotFoundException> {
            editTaskUsecase.editTask("43r34ferc", "new name")
        }
    }

    @Test
    fun `should throw not valid uuid when trying to parse not valid uuid`() {


        assertThrows<IllegalFormatException> {
            editTaskUsecase.editTask("00000000", "new name")
        }
    }




}