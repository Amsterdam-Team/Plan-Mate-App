package ui

import di.appModule
import logic.usecases.LoginUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.controllers.CreateProjectUIController
import ui.controllers.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.project.GetProjectUIController
import ui.project.ViewProjectHistoryUIController


fun main() {
    startKoin{
        modules(
            appModule
        )
    }

    val loginUseCase: LoginUseCase = getKoin().get()
}
