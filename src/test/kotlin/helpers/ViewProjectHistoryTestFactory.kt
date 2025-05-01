package helpers


import logic.entities.LogItem
import java.util.*
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone
import logic.entities.Project

object ViewProjectHistoryTestFactory {

    val PROJECT_1 = Project(
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        name = "Project Alpha",
        states = listOf("Todo", "In Progress", "Done"),
        tasks = emptyList()
    )

    val PROJECT_2 = Project(
        id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
        name = "Project Beta",
        states = listOf("Backlog", "Review", "Complete"),
        tasks = emptyList()
    )

    val ALL_PROJECTS = listOf(PROJECT_1, PROJECT_2)

    private val NOW = Clock.System.now().toLocalDateTime(TimeZone.UTC)

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
