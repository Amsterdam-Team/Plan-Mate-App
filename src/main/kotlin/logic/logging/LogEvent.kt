package logic.logging

import java.util.*

sealed class LogEvent {
    data class ProjectCreated(val projectId: UUID) : LogEvent()
    data class ProjectNameUpdated(val projectId: UUID, val oldName: String, val newName: String) : LogEvent()
    data object InvalidActionAttempted : LogEvent()
}