package data.datasources

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.entities.Task
import logic.exception.PlanMateException
import logic.usecases.task.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CsvDataSourceTest{

    private lateinit var csvDataSource: CsvDataSource<Task>
    private lateinit var fileManager: FileManager<Task>
    private lateinit var csvParser: CsvParser<Task>
    private val id = UUID.randomUUID()
    private val task = CreateTaskTestFactory.validTask
    private val task2 = task.copy(name = "yo", state = "yoyo", id = id)

    private val lines = listOf(
        "${task.id},${task.name},${task.projectId},${task.state}",
        "${task2.id},${task2.name},${task2.projectId},${task.state}"
    )

    private val parsedTasks = listOf(
        task,
        task2
    )

    @BeforeEach
    fun setup(){
        csvParser = mockk(relaxed = true)
        fileManager = mockk(relaxed = true)
        csvDataSource = CsvDataSource(fileManager, csvParser)
    }

    // region getAll
    @Test
    fun `should return list of objects when csv file is valid`() {
        // Given
        every { fileManager.readLines() } returns lines

        // When
        val result = csvDataSource.getAll()

        // Then
        assertThat(result).isEqualTo(parsedTasks)
    }

    @Test
    fun `should throw empty file exception when reading an empty file`() {
        // Given
        every { fileManager.readLines() } returns emptyList()

        // When && Then
        assertThrows<PlanMateException.DataSourceException.EmptyFileException>{
            csvDataSource.getAll()
        }
    }

    // endregion

    // region getById
    @Test
    fun `should return object when given valid object id`() {
        // Given
        every { fileManager.readLines() } returns lines

        // When
        val result = csvDataSource.getById(id)

        // Then
        assertThat(result).isEqualTo(task2)
    }


    @Test
    fun `should throw object does not exist when object id is not found in file`(){
        // Given
        every { fileManager.readLines() } returns emptyList()

        // When && Then
        assertThrows<PlanMateException.DataSourceException.ObjectDoesNotExistException>{
            csvDataSource.getById(id)
        }
    }
    //endregion

    //region add
    @Test
    fun `should add new csv line to file with object`(){
        // Given
        every {  }

        // When
        csvDataSource.add(task)

        // Then
        verify(exactly = 1) { fileManager.appendLine(lines[0]) }

    }

    //endregion

    //region saveAll
    @Test
    fun `should overwrite file with new list of objects when given valid list`(){
        // Given
        every { fileManager.readLines() } returns lines

        // When
        csvDataSource.saveAll(parsedTasks)

        verify { fileManager.writeLines(lines) }
    }

    @Test
    fun `should throw empty data exception when given empty list`(){
        // Given
        val saveObjects = emptyList<Task>()

        // When && Then
        assertThrows<PlanMateException.DataSourceException.EmptyDataException> {
            csvDataSource.saveAll(saveObjects)
        }
    }
    //endregion
}