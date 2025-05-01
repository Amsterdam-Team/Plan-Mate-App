package data.datasources


import com.google.common.truth.Truth.assertThat
import data.datasources.parser.TaskCsvParser
import io.mockk.*
import logic.entities.Task
import logic.exception.PlanMateException.DataSourceException.*
import logic.usecases.task.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CsvDataSourceTest{

    private lateinit var csvDataSource: CsvDataSource<Task>
    private lateinit var fileManager: FileManager<Task>
    private lateinit var csvParser: TaskCsvParser


    @BeforeEach
    fun setup(){
        csvParser = mockk(relaxed = true)
        fileManager = mockk(relaxed = true)
        csvDataSource = CsvDataSource(fileManager, csvParser)
    }

    private fun mockDeserialization(){
        every { csvParser.deserialize(lines[0]) } returns task
        every { csvParser.deserialize(lines[1]) } returns task2
    }

    private fun mockSerialization(){
        every { csvParser.serialize(task) } returns lines[0]
        every { csvParser.serialize(task2) } returns lines[1]
    }

    // region getAll
    @Test
    fun `should return list of objects when csv file is valid`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

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
        assertThrows<EmptyFileException>{
            csvDataSource.getAll()
        }
    }

    // endregion

    // region getById
    @Test
    fun `should return object when given valid object id`() {
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()
        every { csvParser.getId(task) } returns task.id
        every { csvParser.getId(task2) } returns task2.id

        // When
        val result = csvDataSource.getById(task2Id)

        // Then
        assertThat(result).isEqualTo(task2)
    }


    @Test
    fun `should throw object does not exist when object id is not found in file`(){
        // Given
        every { fileManager.readLines() } returns lines
        mockDeserialization()

        // When && Then
        assertThrows<ObjectDoesNotExistException>{
            csvDataSource.getById(notFoundId)
        }
    }
    //endregion

    //region add
    @Test
    fun `should add new csv line to file with object when given valid task`(){
        // Given
        mockSerialization()

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
        mockSerialization()

        // When
        csvDataSource.saveAll(parsedTasks)

        verify { fileManager.writeLines(lines) }
    }

    @Test
    fun `should throw empty data exception when given empty list`(){
        // Given
        val saveObjects = emptyList<Task>()

        // When && Then
        assertThrows<EmptyDataException> {
            csvDataSource.saveAll(saveObjects)
        }
    }
    //endregion

    companion object{
        private val task2Id = UUID.randomUUID()
        private val notFoundId = UUID.randomUUID()
        private val task = CreateTaskTestFactory.validTask
        private val task2 = task.copy(name = "yo", state = "In Progress", id = task2Id)

        private val lines = listOf(
            "${task.id},${task.name},${task.projectId},${task.state}",
            "${task2.id},${task2.name},${task2.projectId},${task.state}"
        )

        private val parsedTasks = listOf(
            task,
            task2
        )
    }
}