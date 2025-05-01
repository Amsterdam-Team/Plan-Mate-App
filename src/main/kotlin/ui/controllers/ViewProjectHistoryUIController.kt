package ui.controllers

import logic.entities.LogItem
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.usecases.ViewProjectHistoryUseCase
import utils.printSwimlanesView

class ViewProjectHistoryUIController(
    private val viewProjectHistoryUseCase: ViewProjectHistoryUseCase,
    private val projectRepository: ProjectRepository,
) {
    fun start(){
        println("hi, please enter the id of the project")
        projectRepository.getProjects().forEach {
            printSwimlanesView(project = it )
        }
        viewProjectHistoryUseCase.execute(readlnOrNull()).forEach {
            printLogItem(it)
        }
    }
    private fun printLogItem(log: LogItem) {
        println(
            """
        ------------------------------
        🆔 Log ID     : ${log.id}
        📝 Message    : ${log.message}
        📅 Date       : ${log.date}
        🔗 Entity ID  : ${log.entityId}
        ------------------------------
        """.trimIndent()
        )
    }
}
