package data.datasources.parser

import kotlinx.datetime.LocalDateTime
import logic.entities.LogItem
import logic.exception.PlanMateException.ParsingException.CsvFormatException
import java.util.UUID

class LogItemCsvParser: CsvParser<LogItem>() {
    override fun serialize(item: LogItem): String {
        return listOf(
            item.id.toString(),
            item.message,
            item.date.toString(),
            item.entityId.toString()
        ).joinToString(",")
    }

    override fun deserialize(item: String): LogItem {
        val parts = item.split(",")
        if (parts.size != 4) {
            throw CsvFormatException
        }
        return try {
            LogItem(
                id = UUID.fromString(parts[0]),
                message = parts[1],
                date = LocalDateTime.parse(parts[2]),
                entityId = UUID.fromString(parts[3])
            )
        } catch (e: Exception){
            throw CsvFormatException
        }
    }

    override fun getId(item: LogItem): UUID {
        return item.id
    }
}