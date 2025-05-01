package data.datasources

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import logic.entities.Task
import logic.exception.PlanMateException
import logic.usecases.task.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CsvParserTest{

    private val task = CreateTaskTestFactory.validTask
    private val line = "${task.id},${task.name},${task.projectId},${task.state}"
    private val incorrectLine = "${task.id},${task.name},${task.projectId},${task.state},0,ug,yhf"
    private lateinit var csvParser: CsvParser<Task>

    @BeforeEach
    fun setup(){
        csvParser = CsvParser()
    }

    @Test
    fun `should return valid object when given valid row`(){
        // Given


        // When
        val result = csvParser.deserialize(line)

        //Then
        assertThat(result).isEqualTo(task)
    }

    @Test
    fun `should return valid csv row when give an object`(){
        // Given

        //When
        val result = csvParser.serialize(task)

        // Then
        assertThat(result).isEqualTo(task)
    }

    @Test
    fun `should throw invalid csv exception when given incorrect row`(){
        // Given
        val incorrectCsvLine = incorrectLine

        // When && Then
        assertThrows<PlanMateException.ParsingException.CsvFormatException>{
            csvParser.deserialize(incorrectCsvLine)
        }
    }
}