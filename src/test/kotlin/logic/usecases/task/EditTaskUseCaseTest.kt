package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import data.repository.task.TaskRepositoryImpl
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import logic.exception.PlanMateException.NotFoundException.*
import java.util.*

class EditTaskUseCaseTest {
    lateinit var repository: TaskRepositoryImpl
    lateinit var usecase: EditTaskUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        usecase = EditTaskUseCase(repository)
    }

    @Test
    fun `should return true when editing task function complete successfully`() {

        // when
        val result = usecase.editTask("43r34ferc", "new name")

        assertThat(result).isTrue()
    }

    @Test
    fun `should throw not found task exception when trying to update not existed task`() {

        assertThrows<TaskNotFoundException> {
            usecase.editTask("43r34ferc", "new name")
        }
    }

    @Test
    fun `should throw not valid uuid when trying to parse not valid uuid`() {
        //TODO: refactor name to better name


        assertThrows<IllegalFormatException> {
            usecase.editTask("00000000", "new name")
        }
    }


}