package ui

import data.datasources.MongoDatabaseFactory
import data.datasources.userDataSource.UserDataSourceInterface
import data.datasources.userDataSource.UserRemoteDataSource
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.inject

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import logic.entities.User
import logic.usecases.LoginUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO
import org.koin.ktor.ext.inject

import ui.controller.CreateTaskUIController
import ui.controllers.CreateProjectUIController
import ui.controllers.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.project.GetProjectUIController
import ui.project.ViewProjectHistoryUIController
import java.util.*
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger


fun main() {
//    startKoin{
//        modules(
//            appModule
//        )
//    }
//
//    val consoleIO : ConsoleIO = getKoin().get()
//    val loginUseCase: LoginUseCase = getKoin().get()
//    val createProjectUIController: CreateProjectUIController = getKoin().get()
//    val deleteTaskUiController: DeleteProjectUiController = getKoin().get()
//    val viewProjectHistoryUIController: ViewProjectHistoryUIController = getKoin().get()
//    val updateStateUiController: UpdateStateUiController = getKoin().get()
//    val getProjectUIController: GetProjectUIController = getKoin().get()
//    val deleteProjectUiController: DeleteProjectUiController = getKoin().get()
//    val viewTaskLogsUIController: ViewTaskLogsUIController = getKoin().get()
//    val createTaskUIController: CreateTaskUIController = getKoin().get()
//
//
//
//    val adminHandler: AdminMenuHandler = AdminMenuHandler(
//        mapOf(
//            1 to createProjectUIController,
//            2 to updateStateUiController,
//            12 to deleteProjectUiController
//        )
//    )
//
//    val mateHandler: MateMenuHandler = MateMenuHandler(
//        mapOf(
//            1 to getProjectUIController,
//        )
//    )
//
//
//
//    val loginUIController = LoginUIController(loginUseCase, adminHandler, mateHandler, consoleIO)
//
//    loginUIController.execute()


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
    }.start(wait = true)
}

