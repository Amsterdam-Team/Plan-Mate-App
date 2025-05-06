package ui.utils

import logic.entities.Task
import ui.console.ConsoleIOImpl
import ui.utils.DisplayUtils.printLine

fun printTasksSwimlanesView(
    tasks: List<Task>,
) {
    val lanes = tasks.map { it.state }.distinct()
    val taskFormatter: (Task) -> String = { "${it.id} - ${it.name}" }
    val columnWidth = tasks.maxOfOrNull { taskFormatter(it).length }?.coerceAtLeast(20) ?: 20
    val laneMap = lanes.associateWith { lane -> tasks.filter { it.state == lane } }
    val maxTasks = laneMap.values.maxOfOrNull { it.size } ?: 0

    DisplayUtils.printTitle("Task Project ")
    printLine = "-".repeat((columnWidth + 3) * lanes.size - 3)

    println(printLine)
    println("\n📋 Task View by Project [ID : ${tasks[0].projectId} ]\n (Swimlanes)")
    println(printLine)
    println(lanes.joinToString(" | ") { it.padEnd(columnWidth) })
    println("-".repeat((columnWidth + 3) * lanes.size - 3))

    for (i in 0 until maxTasks) {
        val row = lanes.joinToString(" | ") { lane ->
            laneMap[lane]?.getOrNull(i)?.let(taskFormatter)?.padEnd(columnWidth)
                ?: " ".repeat(columnWidth)
        }
        println(row)
    }

    println(printLine)

}

fun mainMenuTasks(
    onCreateTask: () -> Unit,
    onViewTaskDetails: () -> Unit,

    ) {
    printLine()
    DisplayUtils.printSubTitle("Main Menu")

    print("[C] Create task New          ")
    print("[V] View task details          ")
    print("[Q] Quit          ")
    println("           ")

    val consoleIO = ConsoleIOImpl()

    val input = consoleIO.readFromUser().trim().uppercase()
    consoleIO.println("option :$input")

    when (input) {
        "C" -> onCreateTask()
        "V" -> onViewTaskDetails()
        "Q" -> return
        else -> println("❌ Invalid option")
    }
}
