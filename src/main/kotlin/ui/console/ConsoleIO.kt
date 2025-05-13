package ui.console

interface ConsoleIO {
    fun println(line: String)
    fun readFromUser(): String
}