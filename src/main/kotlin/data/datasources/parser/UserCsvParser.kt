package data.datasources.parser

import logic.entities.User
import logic.exception.PlanMateException
import java.util.UUID

class UserCsvParser: CsvParser<User>() {
    override fun serialize(item: User): String {
        return listOf(
            item.id.toString(),
            item.username,
            item.password,
            item.isAdmin
        ).joinToString(",")
    }

    override fun deserialize(item: String): User {
        val parts = item.split(",")
        if (parts.size != 4) {
            throw PlanMateException.ParsingException.CsvFormatException
        }
        return try {
            User(
                id = UUID.fromString(parts[0]),
                username = parts[1],
                password = parts[2],
                isAdmin = parts[3].toBoolean()
            )
        } catch (e: Exception){
            throw PlanMateException.ParsingException.CsvFormatException
        }
    }

    override fun getId(item: User): UUID {
        return item.id
    }
}