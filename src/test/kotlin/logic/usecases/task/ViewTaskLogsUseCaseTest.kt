package logic.usecases.task

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.LogRepository
import logic.usecases.ValidateInputUseCase
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import logic.usecases.testFactory.taskLogs
import logic.usecases.testFactory.validId

class ViewTaskLogsUseCaseTest {
    private lateinit var repository : LogRepository
    private lateinit var useCase : ViewTaskLogsUseCase
    private lateinit var validationInputUseCase: ValidateInputUseCase


    @BeforeEach
    fun setup(){
        repository = mockk()
        validationInputUseCase = mockk()
        useCase = ViewTaskLogsUseCase(repository,validationInputUseCase)
    }

    @Test
    fun `should return logs of task when id is valid format of UUID and found`() = runTest {

        every { validationInputUseCase.isValidUUID(validId.toString()) }returns true
        coEvery { repository.viewLogsById(validId) } returns taskLogs()

        val returnedLogs = useCase.viewTaskLogs(validId.toString())

        assertThat(taskLogs()).isEqualTo(returnedLogs)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123e4567-e89b-12d3-a456" ,
            " ",
            "123e4567-e89b-12d3-a456-426614174%@0" ,
            "kahdjshuffl123"
        ]
    )
    fun `should throw InvalidTaskIDException when task id is invalid UUID format`(id:String)= runTest{

        every { validationInputUseCase.isValidUUID(id)} returns false

        assertThrows<InvalidTaskIDException> {
            useCase.viewTaskLogs(id)
        }
    }

    @Test
    fun `should throw TaskNotFoundException when id of task is valid format of UUID but not found`()= runTest{

        every { validationInputUseCase.isValidUUID(validId.toString()) } returns true
        coEvery { repository.viewLogsById(validId) } throws TaskNotFoundException

        assertThrows<TaskNotFoundException> {
            useCase.viewTaskLogs(validId.toString())

        }
    }
}