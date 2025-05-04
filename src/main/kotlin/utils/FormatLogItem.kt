package utils

import logic.entities.LogItem

fun formatLogItem(log: LogItem) =
    """
        ------------------------------
        🆔 Log ID     : ${log.id}
        📝 Message    : ${log.message}
        📅 Date       : ${log.date}
        🔗 Entity ID  : ${log.entityId}
        ------------------------------
        """.trimIndent()