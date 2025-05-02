package ui

import di.appModule
import org.koin.core.context.startKoin

fun main() {
    startKoin{
        modules(
            appModule
        )
    }
}
