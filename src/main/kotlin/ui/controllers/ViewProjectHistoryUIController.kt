package ui.controllers

import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.usecases.ViewProjectHistoryUseCase

class ViewProjectHistoryUIController(
    viewProjectHistoryUseCase: ViewProjectHistoryUseCase,
    projectRepository: ProjectRepository,
    logRepository: LogRepository
) {
    fun viewProjectHistoryUI(){

    }
}