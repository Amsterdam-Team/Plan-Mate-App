package helper

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import logic.entities.Project
import java.util.UUID

object ViewProjectHistoryTestFactory {

    val PROJECT_1 = Project(
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        name = "Project Alpha",
        states = listOf("Todo", "In Progress", "Done"),
        tasks = emptyList()
    )



    private val NOW = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)

    val LOG_1 = LogItem(
        id = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
        message = "Log 1",
        date = NOW,
        entityId = PROJECT_1.id
    )

    val LOG_2 = LogItem(
        id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
        message = "Log 2",
        date = NOW,
        entityId = PROJECT_1.id
    )

    val LOGS_FOR_PROJECT_1 = listOf(LOG_1, LOG_2)
}