package data.datasources

import logic.exception.PlanMateException
import java.io.File

class FileManager<T>(name: String) {

    private val fileName = "$name.csv"
    private val file = File(fileName)

    fun readLines(): List<String> {
        createCsvFile()
        if (file.readLines().isNotEmpty()) {
            return file.readLines()
        } else {
            throw PlanMateException.DataSourceException.EmptyFileException
        }
    }

    fun writeLines(lines: List<String>) {
        createCsvFile()
        file.writeText(lines.joinToString("\n"))
    }

    fun appendLine(line: String) {
        createCsvFile()
        file.appendText(
            if (file.length() > 0){
                "\n$line"
            } else line
        )
    }

    private fun createCsvFile() {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun deleteFile(){
        file.delete()
    }

    companion object {
        inline fun <reified T> create(): FileManager<T> {
            val name = T::class.simpleName?.lowercase() ?: "unknown"
            return FileManager(name)
        }
    }
}