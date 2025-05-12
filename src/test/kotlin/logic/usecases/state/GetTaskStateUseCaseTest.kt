package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ITaskRepository
import helper.ConstantsFactory.EXPECTED_TASK_STATE
import helper.TaskFactory.existingTask
import helper.ConstantsFactory.EXISTING_TASK_ID
import helper.ConstantsFactory.INVALID_TASK_ID
import helper.TaskFactory.notExistingTaskID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetTaskStateUseCaseTest {

    private lateinit var repository: ITaskRepository
    private lateinit var useCase: GetTaskStateUseCase

    @BeforeEach
    fun setup() = runTest{
        repository = mockk()
        useCase = GetTaskStateUseCase(repository)
    }

    @Test
    fun `should return the correct state when the task exists`() = runTest{
        //Given
        coEvery { repository.getTaskById(any()) } returns existingTask

        //When
        val state = useCase.execute(EXISTING_TASK_ID)

        //Then
        assertThat(state).isEqualTo(EXPECTED_TASK_STATE)
    }

    @Test
    fun `should throw InvalidTaskIDException when the task ID is invalid`() = runTest{
        //Given & When & Then
        assertThrows<InvalidTaskIDException> { useCase.execute(INVALID_TASK_ID) }
    }

    @Test
    fun `should throw TaskNotFoundException when the task is not exists`() = runTest{
        //Given
        coEvery { repository.getTaskById(any()) } throws TaskNotFoundException

        //When & Then
        assertThrows<TaskNotFoundException> { useCase.execute(notExistingTaskID) }
    }


}