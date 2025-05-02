package logic.logging

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import java.util.*

fun LogEvent.toLogItem(userId: UUID?): LogItem {
    val message = when (this) {
        is LogEvent.ProjectCreated ->
            "Project created successfully with ID: '${projectId}' "

        is LogEvent.ProjectNameUpdated ->
            "Project name updated from '${oldName}' to '${newName}' for project: '${projectId} by '$userId'"

        LogEvent.InvalidActionAttempted ->
            "An invalid action was attempted."
    }

    return LogItem(
        id = UUID.randomUUID(),
        message = message,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        entityId = when (this) {
            is LogEvent.ProjectCreated -> projectId
            is LogEvent.ProjectNameUpdated -> projectId
            else -> UUID(0, 0)
        }
    )
}
