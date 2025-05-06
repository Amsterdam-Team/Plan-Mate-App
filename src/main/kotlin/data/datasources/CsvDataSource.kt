package data.datasources

import data.datasources.parser.CsvParser
import logic.exception.PlanMateException
import java.util.*

class CsvDataSource<T : Any>(
    private val fileManager: FileManager<T>,
    private val parser: CsvParser<T>,
) : DataSource {
    override fun getAll(): List<Any> {
        val items = fileManager.readLines().map {
            parser.deserialize(it)
        }
        return items.ifEmpty {
            emptyList()
        }
    }

    override fun getById(id: UUID): Any {
        val item = getAll().find { parser.getId(it as T) == id }
        if (item != null) {
            return item
        } else {
            throw PlanMateException.DataSourceException.ObjectDoesNotExistException
        }
    }

    override fun saveAll(items: List<Any>) {
        val lines = items.map { parser.serialize(it as T) }
        fileManager.writeLines(lines)
    }

    override fun add(item: Any) {
        val line = parser.serialize(item as T)
        fileManager.appendLine(line)
    }
}