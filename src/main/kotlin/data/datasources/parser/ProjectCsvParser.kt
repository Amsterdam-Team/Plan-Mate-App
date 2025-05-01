package data.datasources.parser

import logic.entities.Project
import logic.exception.PlanMateException.ParsingException.CsvFormatException
import java.util.*

class ProjectCsvParser: CsvParser<Project>() {
    override fun serialize(item: Project): String {
        return listOf(
            item.id.toString(),
            item.name,
            item.states.joinToString(";")
        ).joinToString(",")
    }

    override fun deserialize(item: String): Project {
        val parts = item.split(",")
        if (parts.size != 3) {
            throw CsvFormatException
        }

        return try {
            Project(
                id = UUID.fromString(parts[0]),
                name = parts[1],
                states = parts[2].split(";"),
                tasks = emptyList()
            )
        } catch (e: Exception) {
            throw CsvFormatException
        }
    }

    override fun getId(item: Project): UUID {
        return item.id
    }
}