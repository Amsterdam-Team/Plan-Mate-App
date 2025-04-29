package usecase

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.TaskIDNotFoundException
import logic.repository.LogRepository
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.taskLogs
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
    fun `should return logs of task when id of task is found`(){
        val id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.viewLogsById(id) } returns taskLogs()

        val returnedLogs = useCase.viewTaskLogs(id)
        assertEquals(taskLogs(), returnedLogs)
    }

   @Test
    fun `should throw TaskIdNotFoundException when id of task not found`(){
        val id = UUID.fromString("123e4588-e11b-12d5-a4c6-426614174000")

        every { repository.viewLogsById(id) } throws TaskIDNotFoundException
        assertThrows<TaskIDNotFoundException> {
           useCase.viewTaskLogs(id)
       }
    }
}