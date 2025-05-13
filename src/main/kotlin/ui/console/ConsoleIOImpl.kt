package ui.console

class ConsoleIOImpl : ConsoleIO {
    override fun println(line: String) = kotlin.io.println(line)

    override fun readFromUser(): String = readln()
}