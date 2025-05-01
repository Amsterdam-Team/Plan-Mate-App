package data.datasources.parser

import logic.entities.Project
import java.util.*

class ProjectCsvParser: CsvParser<Project>() {
    override fun serialize(item: Project): String {
        TODO("Not yet implemented")
    }

    override fun deserialize(item: String): Project {
        TODO("Not yet implemented")
    }

    override fun getId(item: Project): UUID {
        return item.id
    }
}