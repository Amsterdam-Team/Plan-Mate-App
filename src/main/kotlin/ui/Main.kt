package ui

import di.appModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.login.LoginUIController


fun main() {
    startKoin{
        modules(
            appModule
        )
    }

    val loginUIController: LoginUIController = getKoin().get()

    runBlocking {
        loginUIController.execute()
    }


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
    }.start(wait = true)
}

