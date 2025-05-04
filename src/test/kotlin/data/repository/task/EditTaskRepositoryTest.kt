package data.repository.task

import data.datasources.CsvDataSource
import data.datasources.taskDataSource.TaskDataSourceInterface
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

    lateinit var dataSource: TaskDataSourceInterface
    lateinit var repository: TaskRepositoryImpl
    lateinit var authenticationTask: Task

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = TaskRepositoryImpl(mockk())
        authenticationTask = Task(
            id = randomUUID(),
            name = "add auth function",
            projectId = randomUUID(),
            state = "todo"
        )

    }

    @Test
    fun `should return true when editing function complete successfully`() {

    }

    @Test
    fun `should throw not found exception when editing or updating not existed task`() {


    }

    @Test
    fun `should throw empty data exception when editing or updating and there is no tasks`() {


    }

}