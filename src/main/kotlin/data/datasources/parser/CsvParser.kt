package data.datasources.parser

import logic.exception.PlanMateException.ParsingException.CsvFormatException
import java.util.UUID
import kotlin.jvm.Throws

abstract class CsvParser<T>() {
    abstract fun serialize(item: T): String
    abstract fun deserialize(item: String): T
    abstract fun getId(item: T): UUID
}