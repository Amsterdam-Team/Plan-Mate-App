package data.repository.task

import com.google.common.truth.Truth.assertThat
import data.datasources.CsvDataSource
import data.datasources.taskDataSource.TaskDataSourceInterface
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import logic.entities.Task
import logic.exception.PlanMateException
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.DataSourceException.EmptyFileException
import logic.exception.PlanMateException.NotFoundException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.repository.TaskRepository
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
        repository = TaskRepositoryImpl(dataSource)
        authenticationTask = Task(
            id = randomUUID(),
            name = "add auth function",
            projectId = randomUUID(),
            state = "todo"
        )

    }

    @Test
    fun `should return true when editing task name complete successfully`() {
        every { dataSource.updateTaskName(taskId = authenticationTask.id, newName = "new name") } returns true
        val result = repository.updateTaskNameByID(
            taskId = authenticationTask.id,
            newName = "new name"
        )
        assertThat(result).isTrue()


    }

    @Test
    fun `should return true when editing task state complete successfully`() {
        every { dataSource.updateTaskState(taskId = authenticationTask.id, newState = "new state") } returns true
        val result = repository.updateStateNameByID(
            taskId = authenticationTask.id,
            newState = "new state"
        )
        assertThat(result).isTrue()

    }

    @Test
    fun `should throw task not found when editing or updating non existed task`() {
        every { dataSource.updateTaskState(taskId = authenticationTask.id, newState = "new state") } throws TaskNotFoundException
        assertThrows<NotFoundException> {
            repository.updateStateNameByID(
                taskId = authenticationTask.id,
                newState = "new state"
            )
        }


    }

}