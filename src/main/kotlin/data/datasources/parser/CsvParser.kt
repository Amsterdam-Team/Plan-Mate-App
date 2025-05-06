package data.datasources.parser

import java.util.UUID

abstract class CsvParser<T>() {
    abstract fun serialize(item: T): String
    abstract fun deserialize(item: String): T
    abstract fun getId(item: T): UUID
}