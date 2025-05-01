package ui.controllers

import logic.entities.LogItem
import logic.repository.ProjectRepository
import logic.usecases.ViewProjectHistoryUseCase
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView

class ViewProjectHistoryUIController(
    private val viewProjectHistoryUseCase: ViewProjectHistoryUseCase,
    private val projectRepository: ProjectRepository,
) : BaseUIController{

    override fun execute() {
        tryToExecute(
            action = {
                println("hi, please enter the id of the project")
                projectRepository.getProjects().forEach {
                    printSwimlanesView(project = it )
                }
                viewProjectHistoryUseCase.execute(readlnOrNull())
            },
            onSuccess = {
                it.forEach { printLogItem(it) }
            }
        )
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
