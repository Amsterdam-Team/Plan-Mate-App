package data.datasources

import java.io.File
import java.util.*

class CsvDataSource<T> (
    private val fileManager: FileManager<T>,
    private val parser: CsvParser<T>
): DataSource<T> {
    override fun getAll(): List<T> {
        TODO("Not yet implemented")
    }

    override fun getById(id: UUID): T? {
        TODO("Not yet implemented")
    }

    override fun saveAll(items: List<T>) {
        TODO("Not yet implemented")
    }

    override fun add(item: T) {
        TODO("Not yet implemented")
    }
}