package data.datasources

import com.google.common.truth.Truth.assertThat
import logic.entities.Task
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FileManagerTest{
    private lateinit var fileManager: FileManager<Task>
    private val lines = listOf("a,b,c", "1,2,3")
    @BeforeEach
    fun setup(){
        fileManager = FileManager.create<Task>()
        fileManager.writeLines(lines)
    }

    @AfterEach
    fun cleanup(){
        fileManager.deleteFile()
    }

    @Test
    fun `should write the lines correctly to file when given lines`() {
        // Given
        val lines = listOf("a,b,c", "1,2,3")

        // When
        fileManager.writeLines(lines)
        val result = fileManager.readLines()

        // Then
        assertThat(result).isEqualTo(lines)
    }

    @Test
    fun `should append line to file when given input line`() {
        // Given
        val appended = "second,line"

        // When
        fileManager.appendLine(appended)

        // Then
        val result = fileManager.readLines()
        assertThat(result).contains(appended)
    }

    @Test
    fun `should read lines correctly when given file with lines`(){
        // When
        val result = fileManager.readLines()

        // Then
        assertThat(result).isEqualTo(lines)
    }
}