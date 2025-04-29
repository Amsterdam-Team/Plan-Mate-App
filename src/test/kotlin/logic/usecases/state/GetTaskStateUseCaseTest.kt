package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.*
import logic.repository.TaskRepository
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.EXPECTED_TASK_STATE
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.existingTaskID
import logic.usecases.state.testFactory.GetTaskStateUseCaseTestFactory.notExistingTaskID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetTaskStateUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetTaskStateUseCase(repository)
    }

    @Test
    fun `should return the correct state when the task exists`() {
        //Given & When
        val state = useCase.getTaskStateByTaskID(existingTaskID)

        //Then
        assertThat(state).isEqualTo(EXPECTED_TASK_STATE)
    }

    @Test
    fun `should throw TaskNotFoundException when the task is not exists`() {
        //Given & When & Then
        assertThrows<TaskNotFoundException> { useCase.getTaskStateByTaskID(notExistingTaskID) }
    }

}