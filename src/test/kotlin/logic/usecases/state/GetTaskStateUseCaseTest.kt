package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.TaskRepository
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.EXPECTED_TASK_STATE
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.existingTask
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.existingTaskID
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.invalidTaskID
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.notExistingTaskID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetTaskStateUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetTaskStateUseCase(repository)
    }

    @Test
    fun `should return the correct state when the task exists`() {
        //Given
        every { repository.getTaskById(any()) } returns existingTask

        //When
        val state = useCase.execute(existingTaskID)

        //Then
        assertThat(state).isEqualTo(EXPECTED_TASK_STATE)
    }

    @Test
    fun `should throw InvalidTaskIDException when the task ID is invalid`() {
        //Given & When & Then
        assertThrows<InvalidTaskIDException> { useCase.execute(invalidTaskID) }
    }

    @Test
    fun `should throw TaskNotFoundException when the task is not exists`() {
        //Given
        every { repository.getTaskById(any()) } throws TaskNotFoundException

        //When & Then
        assertThrows<TaskNotFoundException> { useCase.execute(notExistingTaskID) }
    }


}