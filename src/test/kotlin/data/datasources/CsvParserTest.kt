package data.datasources

import com.google.common.truth.Truth.assertThat
import data.datasources.parser.CsvParser
import data.datasources.parser.TaskCsvParser
import logic.entities.Task
import logic.exception.PlanMateException
import helper.CreateTaskTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CsvParserTest {


    private lateinit var csvParser: CsvParser<Task>

    @BeforeEach
    fun setup() {
        csvParser = TaskCsvParser()
    }

    @Test
    fun `should return valid object when given valid row`() {
        // When
        val result = csvParser.deserialize(line)

        //Then
        assertThat(result).isEqualTo(task)
    }

    @Test
    fun `should return valid csv row when give an object`() {
        //When
        val result = csvParser.serialize(task)

        // Then
        assertThat(result).isEqualTo(line)
    }


    @ParameterizedTest
    @CsvSource("invalidCsvLines")
    fun `should throw invalid csv exception when given incorrect row`(csv: String) {
        // When && Then
        assertThrows<PlanMateException.ParsingException.CsvFormatException> {
            csvParser.deserialize(csv)
        }
    }


    companion object {
        private val task = CreateTaskTestFactory.validTask
        private val line = "${task.id},${task.name},${task.projectId},${task.state}"
        private val incorrectLine = "${task.id},${task.name},${task.projectId},${task.state},0,ug,yhf"
        private val incorrectOrder = "${task.name},${task.id},${task.projectId},${task.state}"

        fun invalidCsvLines() = listOf(
            incorrectLine,
            incorrectOrder
        )
    }
}