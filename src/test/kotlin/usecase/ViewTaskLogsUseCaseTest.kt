package usecase

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException

import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.LogRepository
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import utils.hepler.taskLogs
import utils.hepler.validId
import java.util.UUID
import kotlin.test.assertEquals

class ViewTaskLogsUseCaseTest {
    private lateinit var repository : LogRepository
    private lateinit var useCase : ViewTaskLogsUseCase


    @BeforeEach
    fun setup(){
        repository = mockk()
        useCase = ViewTaskLogsUseCase(repository)
    }

    @Test
    fun `should return logs of task when id is valid format of UUID and found`(){
        every { repository.viewLogsById(validId) } returns taskLogs()

        val returnedLogs = useCase.viewTaskLogs(validId.toString())

        assertEquals(taskLogs(), returnedLogs)
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
    fun `should throw InvalidTaskIDException when task id is invalid UUID format`(id:String){

        assertThrows<InvalidTaskIDException>{
            useCase.viewTaskLogs(id)
        }
    }

    @Test
    fun `should throw TaskNotFoundException when id of task not found`(){
        every { repository.viewLogsById(validId) } throws TaskNotFoundException

        assertThrows<TaskNotFoundException> {
            useCase.viewTaskLogs(validId.toString())

        }
    }
}