package usecase

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDateTime
import logic.entities.LogItem
import logic.exception.PlanMateException
import logic.repository.LogRepository
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals

class ViewTaskLogsUseCaseTest {
    private lateinit var repository : LogRepository
    private lateinit var useCase : ViewTaskLogsUseCase
    private val taskLogs = listOf<LogItem>(
        LogItem(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            message = "This task name is updated by Hend at 12:30 29-4-2025",
            date = LocalDateTime(2025, 4, 29, 8, 30),
            entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        ),
        LogItem(
            id = UUID.fromString("11111551-22a1-bb11-1111-177588lkhjk1"),
            message = "This task state is updated by Hend at 01:30 29-4-2025",
            date = LocalDateTime(2025, 4, 29, 8, 30),
            entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        )
    )

    @BeforeEach
    fun setup(){
        repository = mockk()
        useCase = ViewTaskLogsUseCase(repository)
    }

    @Test
    fun `should return logs of task when id of task is valid`(){
        val id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { repository.viewLogsById(id) } returns taskLogs

        val returnedLogs = useCase.viewTaskLogs(id)
        assertEquals(taskLogs, returnedLogs)
    }

   @Test
    fun `should throw InvalidTaskIdException when id of task not found`(){
        val id = UUID.fromString("123e4588-e11b-12d5-a4c6-426614174000")

         every { repository.viewLogsById(id) } throws PlanMateException.ValidationException.InvalidTaskIDException
       assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
           useCase.viewTaskLogs(id)
       }
    }
}