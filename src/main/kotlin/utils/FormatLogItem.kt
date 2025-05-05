package utils

import logic.entities.LogItem

fun formatLogItem(log: LogItem) =
    """
        ------------------------------
        📝 Message    : ${log.message}
        📅 Date       : ${log.date}
        ------------------------------
        """.trimIndent()