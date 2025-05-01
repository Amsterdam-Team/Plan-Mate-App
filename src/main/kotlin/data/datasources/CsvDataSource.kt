package data.datasources

import data.datasources.parser.CsvParser
import logic.exception.PlanMateException
import java.util.*
import kotlin.reflect.KClass

class CsvDataSource<T : Any>(
    private val fileManager: FileManager<T>,
    private val parser: CsvParser<T>,
) : DataSource {
    override fun getAll(): List<Any> {
        return try {
            val items = fileManager.readLines().map {
                parser.deserialize(it)
            }
            items.ifEmpty {
                throw PlanMateException.DataSourceException.EmptyFileException
            }
        } catch (e: PlanMateException.DataSourceException.EmptyFileException){
            throw PlanMateException.DataSourceException.EmptyFileException
        }
    }

    override fun getById(id: UUID): Any {
        val item = getAll().find { parser.getId(it as T) == id }
        if (item != null) {
            return item
        } else{
            throw PlanMateException.DataSourceException.ObjectDoesNotExistException
        }
    }

    override fun saveAll(items: List<Any>) {
        if (items.isEmpty()){
            throw PlanMateException.DataSourceException.EmptyDataException
        } else {
            val lines = items.map { parser.serialize(it as T) }
            fileManager.writeLines(lines)
        }
    }

    override fun add(item: Any) {
        val line = parser.serialize(item as T)
        fileManager.appendLine(line)
    }
}