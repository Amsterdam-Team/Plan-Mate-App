package data.datasources

import java.io.File
import java.util.*

class CsvDataSource<T> (
    private val file: File,
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

    override fun deleteById(id: UUID) {
        TODO("Not yet implemented")
    }
}