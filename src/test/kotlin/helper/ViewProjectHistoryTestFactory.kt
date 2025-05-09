package helper

import helper.ProjectFactory.PROJECT_1
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import java.util.UUID

object ViewProjectHistoryTestFactory {





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