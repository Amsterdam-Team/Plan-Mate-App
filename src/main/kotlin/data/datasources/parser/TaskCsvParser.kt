package data.datasources.parser

import logic.entities.Task
import logic.exception.PlanMateException
import java.util.UUID

class TaskCsvParser: CsvParser<Task>() {
    override fun serialize(item: Task): String {
        return listOf(
            item.id.toString(),
            item.name,
            item.projectId.toString(),
            item.state
        ).joinToString(",")
    }

    override fun deserialize(item: String): Task {
        val parts = item.split(",")
        if (parts.size != 4) {
            throw PlanMateException.ParsingException.CsvFormatException
        }
        return try {
            Task(
                id = UUID.fromString(parts[0]),
                name = parts[1],
                projectId = UUID.fromString(parts[2]),
                state = parts[3]
            )
        } catch (e: Exception){
            throw PlanMateException.ParsingException.CsvFormatException
        }
    }

    override fun getId(item: Task): UUID {
        return item.id
    }
}