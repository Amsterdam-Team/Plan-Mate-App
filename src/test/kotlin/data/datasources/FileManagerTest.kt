package data.datasources

import com.google.common.truth.Truth.assertThat
import logic.entities.Task
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class FileManagerTest{
    private lateinit var fileManager: FileManager<Task>
    private lateinit var tempFile: File
    private val lines = listOf("a,b,c", "1,2,3")
    @BeforeEach
    fun setup(){
        fileManager = FileManager()
        tempFile = File.createTempFile("${Task::class.simpleName}", ".csv")
        tempFile.writeText(lines.joinToString("\n"))
    }

    @AfterEach
    fun cleanup(){
        tempFile.delete()
    }

    @Test
    fun `when given lines, should write the lines correctly to file`() {
        // Given
        val lines = listOf("a,b,c", "1,2,3")

        // When
        fileManager.writeLines(lines)
        val result = fileManager.readLines()

        // Then
        assertThat(result).isEqualTo(lines)
    }

    @Test
    fun `when given input line should append line to file`() {
        // Given
        val appended = "second,line"

        // When
        fileManager.appendLine(appended)

        // Then
        val result = fileManager.readLines()
        assertThat(result).contains(appended)
    }

    @Test
    fun `should read lines correctly`(){
        // When
        val result = fileManager.readLines()

        // Then
        assertThat(result).isEqualTo(lines)
    }
}