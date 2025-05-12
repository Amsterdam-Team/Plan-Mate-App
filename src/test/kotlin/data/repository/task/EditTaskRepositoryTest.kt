package data.repository.task
import com.google.common.truth.Truth.assertThat
import data.datasources.taskDataSource.ITaskDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.Task
import logic.exception.PlanMateException.NotFoundException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID.randomUUID

class EditTasKRepositoryTest {

    lateinit var dataSource: ITaskDataSource
    lateinit var repository: TaskRepository
    lateinit var authenticationTask: Task

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = TaskRepository(dataSource)
        authenticationTask = Task(
            id = randomUUID(),
            name = "add auth function",
            projectId = randomUUID(),
            state = "todo"
        )

    }

    @Test
    fun `should return true when editing task name complete successfully`() = runTest{
        coEvery { dataSource.updateTaskName(taskId = authenticationTask.id, newName = "new name") } returns true
        val result = repository.updateTaskNameByID(
            taskId = authenticationTask.id,
            newName = "new name"
        )
        assertThat(result).isTrue()


    }

    @Test
    fun `should return true when editing task state complete successfully`() = runTest{
        coEvery { dataSource.updateTaskState(taskId = authenticationTask.id, newState = "new state") } returns true
        val result = repository.updateStateNameByID(
            taskId = authenticationTask.id,
            newState = "new state"
        )
        assertThat(result).isTrue()

    }

    @Test
    fun `should throw task not found when editing or updating non existed task`()= runTest {
        coEvery { dataSource.updateTaskState(taskId = authenticationTask.id, newState = "new state") } throws TaskNotFoundException
        assertThrows<NotFoundException> {
            repository.updateStateNameByID(
                taskId = authenticationTask.id,
                newState = "new state"
            )
        }


    }

}