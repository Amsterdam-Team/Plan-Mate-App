package ui.utils

import logic.entities.Task
import ui.console.ConsoleIOImpl
import ui.utils.DisplayUtils.printLine
const val  TASK_PROJECT_KEYWORD =  "Task Project"
const val ID_KEYWORD = "ID"
const val TASK_VIEW_MESSAGE = "\n📋 Task View by Project"
const val SWIMLANES_KEYWORD = "(Swimlanes)"
const val MAIN_MENU_KEYWORD = "Main Menu"
const val SELECT_OPTION_MESSAGE = "Tip: Select an option."
const val  CREATE_NEW_TASK_MESSAGE ="[C] Create task New          "
const val VIEW_TASK_DETAILS_MESSAGE = "[V] View task details          "
const val QUIT_KEYWORD = "[Q] Quit          "
const val OPTION_KEYWORD = "Option"
const val INVALID_KEYWORD = "Invalid"

fun printTasksSwimlanesView(
    tasks: List<Task>,

    ) {
    val lanes = tasks.map { it.state }.distinct()
    val taskFormatter: (Task) -> String = { "${it.id} - ${it.name}" }
    val columnWidth = tasks.maxOfOrNull { taskFormatter(it).length }?.coerceAtLeast(20) ?: 20

    val laneMap = lanes.associateWith { lane -> tasks.filter { it.state == lane } }
    val maxTasks = laneMap.values.maxOfOrNull { it.size } ?: 0

    DisplayUtils.printTitle(TASK_PROJECT_KEYWORD)
    printLine = "-".repeat((columnWidth + 3) * lanes.size - 3)

    println(printLine)
    println("$TASK_VIEW_MESSAGE [$ID_KEYWORD : ${tasks[0].projectId} ]\n ($SWIMLANES_KEYWORD")
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

suspend fun mainMenuTasks(
    onCreateTask: suspend () -> Unit,
    onViewTaskDetails: suspend () -> Unit,

    ) {
    DisplayUtils.printBoxedMessage(SELECT_OPTION_MESSAGE)
    printLine()
    DisplayUtils.printSubTitle(MAIN_MENU_KEYWORD)

    print(CREATE_NEW_TASK_MESSAGE)
    print(VIEW_TASK_DETAILS_MESSAGE)
    print(QUIT_KEYWORD)
    println("           ")

    val consoleIO = ConsoleIOImpl()

    val input = consoleIO.readFromUser().trim().uppercase()
    consoleIO.println("$OPTION_KEYWORD :$input")

    when (input) {
        "C" -> onCreateTask()
        "V" -> onViewTaskDetails()
        "Q" -> return
        else -> println("$INVALID_KEYWORD $OPTION_KEYWORD")
    }
}
