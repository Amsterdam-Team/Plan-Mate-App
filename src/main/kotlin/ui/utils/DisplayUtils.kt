package ui.utils

object DisplayUtils {
    var printLine = ""


    // ANSI Colors
    private const val RESET = "\u001B[0m"
    private const val BLUE = "\u001B[34m"
    private const val GREEN = "\u001B[32m"
    private const val CYAN = "\u001B[36m"
    private const val YELLOW = "\u001B[33m"
    private const val MAGENTA = "\u001B[35m"
    private const val RED = "\u001B[31m"
    private const val BOLD = "\u001B[1m"

    fun printLine(length: Int = 50) {
        println(CYAN + "─".repeat(length) + RESET)
    }

    fun printDoubleLine(length: Int = 50) {
        println(GREEN + "═".repeat(length) + RESET)
    }

    fun printDashedLine(length: Int = 50) {
        println(YELLOW + "- ".repeat(length / 2) + RESET)
    }

    fun printSectionSeparator(columnWidth: Int, columns: Int) {
        val totalLength = (columnWidth + 3) * columns - 3
        printLine(totalLength)
    }

    fun printTitle(title: String, width: Int = 70) {
        printDoubleLine(width)
        println(BLUE + BOLD + "🔷 ${title.uppercase()}" + RESET)
        printDoubleLine(width)
    }

    fun printSubTitle(subTitle: String) {
        println(MAGENTA + "▶ $subTitle" + RESET)
        printLine()
    }

    fun promptInput(message: String) {
        println(BOLD + "👉 $message" + RESET)
    }

    fun printSuccess(message: String) {
        println(GREEN + "✔ $message" + RESET)
    }

    fun printError(message: String) {
        println(RED + "✖ $message" + RESET)
    }

    // ✅ NEW: Boxed Message
    fun printBoxedMessage(message: String, padding: Int = 2) {
        val lines = message.lines()
        val maxLineLength = lines.maxOf { it.length }
        val boxWidth = maxLineLength + padding * 2 + 2

        println(CYAN + "╔" + "═".repeat(boxWidth - 2) + "╗")
        for (line in lines) {
            val padded = line.padEnd(maxLineLength)
            println("║" + " ".repeat(padding) + padded + " ".repeat(padding) + "║")
        }
        println("╚" + "═".repeat(boxWidth - 2) + "╝" + RESET)
    }

    // ✅ NEW: Side Comment
    fun printWithSideComment(label: String, comment: String, width: Int = 40) {
        val paddedLabel = label.padEnd(width)
        println("$paddedLabel ${CYAN}# $comment$RESET")
    }
}
