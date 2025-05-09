package logic.usecases.logs

import com.google.common.truth.Truth
import helper.LogTestFactory.taskLogs
import helper.LogTestFactory.validId
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.repository.LogRepository
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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

        every { validationInputUseCase.isValidUUID(validId.toString()) } returns true
        coEvery { repository.viewLogsById(validId) } returns taskLogs()

        val returnedLogs = useCase.viewTaskLogs(validId.toString())

        Truth.assertThat(taskLogs()).isEqualTo(returnedLogs)
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
    fun `should throw InvalidTaskIDException when task id is invalid UUID format`(id:String)= runTest {

        every { validationInputUseCase.isValidUUID(id) } returns false

        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            useCase.viewTaskLogs(id)
        }
    }

    @Test
    fun `should throw TaskNotFoundException when id of task is valid format of UUID but not found`()= runTest {

        every { validationInputUseCase.isValidUUID(validId.toString()) } returns true
        coEvery { repository.viewLogsById(validId) } throws PlanMateException.NotFoundException.TaskNotFoundException

        assertThrows<PlanMateException.NotFoundException.TaskNotFoundException> {
            useCase.viewTaskLogs(validId.toString())

        }
    }
}