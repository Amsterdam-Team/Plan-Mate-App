package data.repository.task

import data.datasources.CsvDataSource
import data.repository.project.ProjectRepositoryImpl
import io.mockk.mockk
import logic.entities.Project
import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*
import java.util.UUID.randomUUID

class EditTasKTest {

    lateinit var dataSource: CsvDataSource<Task>
    lateinit var repository: TaskRepositoryImpl
    lateinit var newTask: Task

    @BeforeEach
    fun setUp() {
        dataSource = CsvDataSource(mockk(), mockk())
        repository = TaskRepositoryImpl(dataSource)
        newTask = Task(
            id = randomUUID(),
            name = "newTask",
            projectId = randomUUID(),
            state = "todo"
        )

    }

    @Test
    fun `should edit task successfully when editing function complete successfully`() {

        assertDoesNotThrow {
            repository.updateTask(newTask)
        }
    }

    @Test
    fun `should throw not found exception when editing or updating not existed task`() {

        assertThrows<TaskNotFoundException> {
            repository.updateTask(newTask)
        }
    }

}