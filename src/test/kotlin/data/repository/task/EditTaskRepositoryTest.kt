package data.repository.task

import data.datasources.CsvDataSource
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.DataSourceException.EmptyFileException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID.randomUUID

class EditTasKRepositoryTest {

    lateinit var dataSource: CsvDataSource<Task>
    lateinit var repository: TaskRepositoryImpl
    lateinit var authTask: Task

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = TaskRepositoryImpl(dataSource)
        authTask = Task(
            id = randomUUID(),
            name = "add auth function",
            projectId = randomUUID(),
            state = "todo"
        )

    }

    @Test
    fun `should edit task successfully when editing function complete successfully`() {

        every {dataSource.getAll()} returns listOf(authTask)
        every { dataSource.saveAll(any())} returns Unit

        assertDoesNotThrow {
            repository.updateTask(authTask.copy(name= "implement authentication"))
        }
    }

    @Test
    fun `should throw not found exception when editing or updating not existed task`() {

        every{dataSource.getAll()} returns listOf(authTask)

        val uiTask= Task( id = randomUUID(),
            name = "add ui ",
            projectId = randomUUID(),
            state = "todo")
        assertThrows<TaskNotFoundException> {
            repository.updateTaskNameByID(uiTask.id, "add ui structure")
        }
    }

    @Test
    fun `should throw empty data exception when editing or updating and there is no tasks`() {

        every{dataSource.getAll()} throws EmptyFileException
        val uiTask= Task( id = randomUUID(),
            name = "add ui ",
            projectId = randomUUID(),
            state = "todo")
        assertThrows<EmptyDataException> {
            repository.updateTaskNameByID(uiTask.id, "add ui structure")
        }
    }

}