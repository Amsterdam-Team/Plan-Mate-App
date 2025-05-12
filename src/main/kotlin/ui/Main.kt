package ui

import di.appModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.login.LoginUIController
import ui.menuHandler.ProjectsView


fun main() {
    startKoin{
        modules(
            appModule
        )
    }

    val loginUIController: LoginUIController = getKoin().get()
    val projectsView: ProjectsView = getKoin().get()
    runBlocking {
        loginUIController.execute()
        projectsView.start()
    }


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
    }.start(wait = true)
}

